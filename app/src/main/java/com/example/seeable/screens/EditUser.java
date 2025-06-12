package com.example.seeable.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.seeable.R;
import com.example.seeable.model.User;
import com.example.seeable.services.AuthenticationService;
import com.example.seeable.services.DatabaseService;
import com.example.seeable.utils.SharedPreferencesUtil;
import com.example.seeable.utils.Validator;
import com.google.android.material.snackbar.Snackbar;

public class EditUser extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "UserProfileActivity";

    EditText etEditFname, etEditLname, etEditPhone;
    Button btnUpdateUser;
    User selectedUser;
    private DatabaseService databaseService;
    String selectedUid;
    boolean isCurrentUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Init();

        databaseService = DatabaseService.getInstance();

        selectedUid = getIntent().getStringExtra("USER_UID");
        User currentUser = SharedPreferencesUtil.getUser(this);
        if (selectedUid == null) {
            selectedUid = currentUser.getId();
        }
        isCurrentUser = selectedUid.equals(currentUser.getId());
        if (!isCurrentUser && !currentUser.isAdmin()) {
            // If the user is not an admin and the selected user is not the current user
            // then finish the activity
            Toast.makeText(this, "You are not authorized to view this profile", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        DatabaseService.getInstance().getUser(AuthenticationService.getInstance().getCurrentUserId(), new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User user) {
                updateView(user);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    private void updateView(User user) {
        this.selectedUser = user;
        etEditFname.setText(user.getFname());
        etEditLname.setText(user.getLname());
        etEditPhone.setText(user.getPhone());
    }

    private void Init() {
        etEditFname= findViewById(R.id.etFN);
        etEditLname= findViewById(R.id.etLN);
        etEditPhone= findViewById(R.id.etP);
        btnUpdateUser= findViewById(R.id.btnUpdateUser);
        btnUpdateUser.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnUpdateUser) {
            updateUser();
            Intent go = new Intent(this, HomePage.class);
            startActivity(go);
        }
    }



    private void updateUser() {
        if (selectedUser == null) {
            Log.e(TAG, "User not found");
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            return;
        }
        // Get the updated user data from the EditText fields
        String firstName = etEditFname.getText().toString();
        String lastName = etEditLname.getText().toString();
        String phone = etEditPhone.getText().toString();

        if (!isValid(firstName, lastName, phone)) {
            Log.e(TAG, "Invalid input");
            return;
        }

        // Update the user object
        selectedUser.setFname(firstName);
        selectedUser.setLname(lastName);
        selectedUser.setPhone(phone);
        // Update the user data in the authentication
        Log.d(TAG, "Updating user profile");
        databaseService.createNewUser(selectedUser, new DatabaseService.DatabaseCallback<Object>() {
            @Override
            public void onCompleted(Object v) {
                Log.d(TAG, "Profile updated successfully");
                // Save the updated user data to shared preferences
                if (isCurrentUser) {
                    SharedPreferencesUtil.saveUser(getApplicationContext(), selectedUser);
                }
                Snackbar.make(EditUser.this, findViewById(android.R.id.content), "Profile updated successfully", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Error updating profile", e);
                Toast.makeText(EditUser.this, "Failed to update profile " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValid(String firstName, String lastName, String phone) {
        if (!Validator.isNameValid(firstName)) {
            etEditFname.setError("First name is required");
            etEditFname.requestFocus();
            return false;
        }
        if (!Validator.isNameValid(lastName)) {
            etEditLname.setError("Last name is required");
            etEditLname.requestFocus();
            return false;
        }
        if (!Validator.isPhoneValid(phone)) {
            etEditPhone.setError("Phone number is required");
            etEditPhone.requestFocus();
            return false;
        }
        return true;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}