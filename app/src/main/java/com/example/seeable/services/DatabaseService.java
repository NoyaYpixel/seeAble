package com.example.seeable.services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.seeable.model.Child;
import com.example.seeable.model.User;
import com.example.seeable.model.UserTeam;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    private static final String TAG = "DatabaseService";

    public interface DatabaseCallback<T> {
        void onCompleted(T object);

        void onFailed(Exception e);
    }

    private static DatabaseService instance;
    private final DatabaseReference databaseReference;

    private DatabaseService() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    private void writeData(String path, Object data, @Nullable DatabaseCallback callback) {
        databaseReference.child(path).setValue(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (callback == null) return;
                callback.onCompleted(null);
            } else {
                if (callback == null) return;
                callback.onFailed(task.getException());
            }
        });
    }

    private <T> void getData(@NotNull final String path, @NotNull final Class<T> clazz, @NotNull final DatabaseCallback<T> callback) {
        readData(path).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                callback.onFailed(task.getException());
                return;
            }
            T data = task.getResult().getValue(clazz);
            callback.onCompleted(data);
        });
    }

    private DatabaseReference readData(String path) {
        return databaseReference.child(path);
    }

    public void createNewUser(User user, DatabaseCallback<Object> callback) {
        writeData("Users/" + user.getId(), user, callback);
    }

    public void getUser(String userId, DatabaseCallback<User> callback) {
        getData("Users/" + userId, User.class, callback);
    }

    public void createNewUserTeam(UserTeam userTeam, DatabaseCallback<Object> callback) {
        writeData("userTeams/" + userTeam.getId(), userTeam, callback);
    }

    public String getNewFoodId() {
        return databaseReference.child("foods").push().getKey();
    }


    public void getUserTeam(String UserTeamId, DatabaseCallback<UserTeam> callback) {
        getData("userTeams/" + UserTeamId, UserTeam.class, callback);
    }

    public void getUserTeams(DatabaseCallback<List<UserTeam>> callback) {
        readData("userTeams").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Error getting data", task.getException());
                    if (callback != null) {
                        callback.onFailed(task.getException());
                    }
                    return;
                }
                List<UserTeam> userTeams = new ArrayList<>();
                task.getResult().getChildren().forEach(dataSnapshot -> {
                    UserTeam userTeam = dataSnapshot.getValue(UserTeam.class);
                    Log.d(TAG, "Got User Team: " + userTeam);
                    userTeams.add(userTeam);
                });

                if (callback != null) {
                    callback.onCompleted(userTeams);
                }
            }
        });
    }


    public void createNewChild(Child child, DatabaseCallback<Object> callback) {
        writeData("AddChild/" + child.getId(), child, callback);
    }

    public String getNewChildId() {
        return databaseReference.child("AddChild").push().getKey();
    }


    public void getChild(String ChildId, DatabaseCallback<Child> callback) {
        getData("child/" + ChildId, Child.class, callback);
    }

    public void getChildren(DatabaseCallback<List<Child>> callback) {
        readData("children").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Error getting data", task.getException());
                    if (callback != null) {
                        callback.onFailed(task.getException());
                    }
                    return;
                }
                List<Child> children = new ArrayList<>();
                task.getResult().getChildren().forEach(dataSnapshot -> {
                    Child child = dataSnapshot.getValue(Child.class);
                    Log.d(TAG, "Got User Team: " + child);
                    children.add(child);
                });

                if (callback != null) {
                    callback.onCompleted(children);
                }
            }
        });
    }

}