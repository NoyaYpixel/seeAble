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
import com.example.seeable.services.AuthenticationService;
import com.example.seeable.services.DatabaseService;
import com.example.seeable.utils.SharedPreferencesUtil;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etPassword;
    Button btnLog;
    String email, pass;
    private AuthenticationService authenticationService;
    private DatabaseService databaseService;
    private User user=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
         user = SharedPreferencesUtil.getUser(this);
        if (user != null) {
            etEmail.setText(user.getEmail());
            etPassword.setText(user.getPassword());
        }
    }

    private void initViews() {
        etEmail= findViewById(R.id.etEmailLogin);
        etPassword= findViewById(R.id.etPasswordLogin);
        btnLog = findViewById(R.id.btnLog);
        btnLog.setOnClickListener(this);

        authenticationService = AuthenticationService.getInstance();
        databaseService = DatabaseService.getInstance();
    }

    @Override
    public void onClick(View view) {
        email = etEmail.getText().toString();
        pass = etPassword.getText().toString();
        authenticationService.signIn(email, pass, new AuthenticationService.AuthCallback<String>() {
            @Override
            public void onCompleted(String uid) {
                databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {
                    @Override
                    public void onCompleted(User user) {
                        // Sign in success, update UI with the signed-in user's information
                       // SharedPreferencesUtil.saveUser(Login.this, user);
                        Log.d("TAG", "signInWithEmail:success");
                        Intent go = new Intent(getApplicationContext(), SendAPublicMessage.class);
                        startActivity(go);
                    }

                    @Override
                    public void onFailed(Exception e) {
                        Log.w("TAG", "signInWithEmail:failure", e);
                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        authenticationService.signOut();
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {
                Log.w("TAG", "signInWithEmail:failure", e);
                Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}