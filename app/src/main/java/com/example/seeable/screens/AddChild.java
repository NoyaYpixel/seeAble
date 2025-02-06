package com.example.seeable.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.seeable.R;
import com.example.seeable.services.AuthenticationService;

public class AddChild extends AppCompatActivity {
    EditText id, fname, lname, birthDate, details;
    Button btnAddChild;

    AuthenticationService authenticationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_child);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        authenticationService = AuthenticationService.getInstance();
        Init();
    }

    private void Init() {
        id = findViewById(R.id.etCId);
        fname = findViewById(R.id.etCFName);
        lname = findViewById(R.id.etCLName);
    }

    public void onClick(View view) {
        if (view == btnAddChild) {
            Intent go = new Intent(getApplicationContext(), ChildrenList.class);
            startActivity(go);
        }
        authenticationService.getCurrentUserId();
    }
}