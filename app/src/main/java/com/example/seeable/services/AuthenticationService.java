package com.example.seeable.services;

import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationService {
    public interface AuthCallback {
        void onCompleted(String uid);
        void onFailed(Exception e);
    }


    private final FirebaseAuth mAuth;

    private static AuthenticationService instance;

    private AuthenticationService() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static AuthenticationService getInstance() {
        if (instance == null) {
            instance = new AuthenticationService();
        }
        return instance;
    }

    public void signIn(String email, String password, AuthCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onCompleted(getCurrentUserId());
            } else {
                callback.onFailed(task.getException());
            }
        });
    }

    public void signUp(String email, String password, AuthCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onCompleted(getCurrentUserId());
            } else {
                callback.onFailed(task.getException());
            }
        });
    }

    public void signOut() {
        mAuth.signOut();
    }

    public String getCurrentUserId() {
        return mAuth.getCurrentUser().getUid();
    }

    public boolean isUserSignedIn() {
        return mAuth.getCurrentUser() != null;
    }
}
