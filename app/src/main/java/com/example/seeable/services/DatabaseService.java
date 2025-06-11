package com.example.seeable.services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.seeable.model.Child;
import com.example.seeable.model.Report;
import com.example.seeable.model.Message;
import com.example.seeable.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

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

    /// run a transaction on the data at a specific path </br>
    /// good for incrementing a value or modifying an object in the database
    /// @param path the path to run the transaction on
    /// @param clazz the class of the object to return
    /// @param function the function to apply to the current value of the data
    /// @param callback the callback to call when the operation is completed
    /// @see DatabaseReference#runTransaction(Transaction.Handler)
    private <T> void runTransaction(@NotNull final String path, @NotNull final Class<T> clazz, @NotNull UnaryOperator<T> function, @NotNull final DatabaseCallback<T> callback) {
        readData(path).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                T currentValue = currentData.getValue(clazz);
                if (currentValue == null) {
                    currentValue = function.apply(null);
                } else {
                    currentValue = function.apply(currentValue);
                }
                currentData.setValue(currentValue);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                if (error != null) {
                    Log.e(TAG, "Transaction failed", error.toException());
                    callback.onFailed(error.toException());
                    return;
                }
                T result = currentData != null ? currentData.getValue(clazz) : null;
                callback.onCompleted(result);
            }
        });

    }

    public void getClassRef(String uid, DatabaseCallback<String> callback) {
        databaseReference.child("classes").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                    if (map == null) {
                        if (callback != null) {
                            callback.onFailed(new Exception("classes not exist"));
                        }
                        return;
                    }
                    Log.d(TAG, map.toString());
                    Log.d(TAG, uid);
                    if (!map.containsKey(uid)) {
                        if (callback != null) {
                            callback.onFailed(new Exception("classes for uid not exist"));
                        }
                        return;
                    }
                    String className = map.get(uid);
                    if (callback != null) {
                        callback.onCompleted(className);
                    }
                } else {
                    if (callback != null) {
                        callback.onFailed(new Exception("Class not exist"));
                    }
                }
            } else {
                if (callback != null) {
                    callback.onFailed(task.getException());
                }
            }
        });
    }
    private void deleteData(@NotNull final String path, @Nullable final DatabaseCallback<Void> callback) {
        readData(path).removeValue((error, ref) -> {
            if (error != null) {
                if (callback == null) return;
                callback.onFailed(error.toException());
            } else {
                if (callback == null) return;
                callback.onCompleted(null);
            }
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

    public void getUsers(DatabaseCallback<List<User>> callback) {
        getDataList("Users", User.class, callback);
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

    public <T> void createNewClassRef(String uid, Class<T> clazz, DatabaseCallback<Object> callback) {
        Map<String, Object> map = new HashMap<>();
        map.put(uid, clazz.getName());
        databaseReference.child("classes").updateChildren(map).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (callback == null) return;
                callback.onCompleted(null);
            } else {
                if (callback == null) return;
                callback.onFailed(task.getException());
            }
        });
    }

    public void deleteChild(@NotNull final String childId, @Nullable final DatabaseCallback<Void> callback) {
        deleteData("child/" + childId, callback);
    }

    public void addDailyReport(@NotNull final Report report, @Nullable final DatabaseCallback<Void> callback) {
        runTransaction("child/" + report.getChildId(), Child.class, new UnaryOperator<Child>() {
            @Override
            public Child apply(Child child) {
                List<Report> reports = child.getDailyReports();
                reports.remove(report);
                reports.add(report);
                child.setDailyReports(reports);
                return child;
            }
        }, new DatabaseCallback<Child>() {
            @Override
            public void onCompleted(Child object) {
                callback.onCompleted(null);
            }

            @Override
            public void onFailed(Exception e) {
                callback.onFailed(e);
            }
        });
    }


}