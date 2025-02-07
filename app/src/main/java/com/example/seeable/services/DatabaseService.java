package com.example.seeable.services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.seeable.model.Child;
import com.example.seeable.model.Message;
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

    private void writeData(String path, Object data, @Nullable DatabaseCallback<Object> callback) {
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

    /// get a list of data from the database at a specific path
    /// @param path the path to get the data from
    /// @param clazz the class of the objects to return
    /// @param callback the callback to call when the operation is completed
    private <T> void getDataList(@NotNull final String path, @NotNull final Class<T> clazz, @NotNull final DatabaseCallback<List<T>> callback) {
        readData(path).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                callback.onFailed(task.getException());
                return;
            }
            List<T> tList = new ArrayList<>();
            task.getResult().getChildren().forEach(dataSnapshot -> {
                T t = dataSnapshot.getValue(clazz);
                tList.add(t);
            });

            callback.onCompleted(tList);
        });
    }

    /// generate a new id for a new object in the database
    /// @param path the path to generate the id for
    /// @return a new id for the object
    /// @see String
    /// @see DatabaseReference#push()
    private String generateNewId(@NotNull final String path) {
        return databaseReference.child(path).push().getKey();
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

    public void getUsers(DatabaseCallback<List<User>> callback) {
        getDataList("Users", User.class, callback);
    }

    public void createNewUserTeam(UserTeam userTeam, DatabaseCallback<Object> callback) {
        writeData("userTeams/" + userTeam.getId(), userTeam, callback);
    }

    public String getNewUserTeamId() {
        return generateNewId("userTeams");
    }


    public void getUserTeam(String UserTeamId, DatabaseCallback<UserTeam> callback) {
        getData("userTeams/" + UserTeamId, UserTeam.class, callback);
    }

    public void getUserTeams(DatabaseCallback<List<UserTeam>> callback) {
        getDataList("userTeams", UserTeam.class, callback);
    }


    public void createNewChild(Child child, DatabaseCallback<Object> callback) {
        writeData("child/" + child.getId(), child, callback);
    }

    public String getNewChildId() {
        return generateNewId("child");
    }


    public void getChild(String ChildId, DatabaseCallback<Child> callback) {
        getData("child/" + ChildId, Child.class, callback);
    }

    public void getChildren(DatabaseCallback<List<Child>> callback) {
        getDataList("child", Child.class, callback);
    }



    public void createNewMessage(Message message, DatabaseCallback<Object> callback) {
        writeData("message/" + message.getId(), message, callback);
    }

    public String getNewMessageId() {
        return generateNewId("message");
    }


    public void getMessage(String messageId, DatabaseCallback<Message> callback) {
        getData("message/" + messageId, Message.class, callback);
    }

    public void getMessages(DatabaseCallback<List<Message>> callback) {
        getDataList("message", Message.class, callback);
    }


}