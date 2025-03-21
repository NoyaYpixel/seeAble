package com.example.seeable.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.seeable.utils.SharedPreferencesUtil;

import com.example.seeable.services.AuthenticationService;
import com.example.seeable.services.DatabaseService;


public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText etFname, etLname, etPhone, etEmail, etPassword;


    Button btnReg;
    private DatabaseService databaseService;
    private AuthenticationService authenticationService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Init();
    }

    private void Init() {
        etFname = findViewById(R.id.etFname);
        etLname = findViewById(R.id.etLname);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnReg = findViewById(R.id.btnReg);
        btnReg.setOnClickListener(this);
        databaseService = DatabaseService.getInstance();
        authenticationService = AuthenticationService.getInstance();

    }

    @Override
    public void onClick(View v) {
        String fname = etFname.getText().toString();
        String lname = etLname.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        //check if registration is valid
        if (!isValid(fname, lname, phone, email, password)) {
            return;
        }

        authenticationService.signUp(email, password, new AuthenticationService.AuthCallback<String>() {
            @Override
            public void onCompleted(String uid) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("TAG", "createUserWithEmail:success");
                User newUser = new User(uid, fname, lname, phone, email, password, null, false);

                databaseService.createNewUser(newUser, new DatabaseService.DatabaseCallback<Object>() {
                    @Override
                    public void onCompleted(Object object) {
                        SharedPreferencesUtil.saveUser(Register.this, newUser);

                        Intent goHome = new Intent(Register.this, HomePage.class);
                        goHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(goHome);
                    }

                    @Override
                    public void onFailed(Exception e) {
                        Log.w("TAG", "createUserWithEmail:failure", e);
                        Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {
                // If sign in fails, display a message to the user.
                Log.w("TAG", "createUserWithEmail:failure", e);
                Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isValid(String fname, String lname, String phone, String email, String password) {
        if (fname.length() < 2) {
            Toast.makeText(Register.this, "שם פרטי קצר מדי", Toast.LENGTH_LONG).show();
            return false;
        }
        if (lname.length() < 2) {
            Toast.makeText(Register.this, "שם משפחה קצר מדי", Toast.LENGTH_LONG).show();
            return false;
        }
        if (phone.length() < 9 || phone.length() > 10) {
            Toast.makeText(Register.this, "מספר הטלפון לא תקין", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!email.contains("@")) {
            Toast.makeText(Register.this, "כתובת האימייל לא תקינה", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(Register.this, "הסיסמה קצרה מדי", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.length() > 20) {
            Toast.makeText(Register.this, "הסיסמה ארוכה מדי", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}