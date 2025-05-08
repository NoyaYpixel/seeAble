package com.example.seeable.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.seeable.R;
import com.example.seeable.model.Child;
import com.example.seeable.model.MyDate;
import com.example.seeable.services.AuthenticationService;
import com.example.seeable.services.DatabaseService;
import com.example.seeable.utils.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddChild extends MyBaseActivity {
    EditText etId, etFname, etLname, etDetails;
    CalendarView birthdayC;
    Button btnAddChild;
    AuthenticationService authenticationService;
    DatabaseService databaseService;

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
        databaseService = DatabaseService.getInstance();
        Init();
    }

    private void Init() {
        etId = findViewById(R.id.etCId);
        etFname = findViewById(R.id.etCFName);
        etLname = findViewById(R.id.etCLName);
        birthdayC = findViewById(R.id.birthdayC);
        etDetails = findViewById(R.id.etCommentsC);

        btnAddChild.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        if (view == btnAddChild) {
            saveChild();
            return;
        }
    }


    private void saveChild() {
        if (!isValid()) {
            Toast.makeText(this, "Save Error", Toast.LENGTH_LONG).show();
            return;
        }

        Child child = getChild();

        databaseService.createNewChild(child, new DatabaseService.DatabaseCallback<Object>() {
            @Override
            public void onCompleted(Object object) {
                Toast.makeText(AddChild.this, "YAY!! Child added successfully", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    @NonNull
    private Child getChild() {
        String id = etId.getText().toString();
        String fname = etFname.getText().toString();
        String lname = etLname.getText().toString();

        Date date = new Date(birthdayC.getDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        MyDate birthday = new MyDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        String notes = etDetails.getText().toString();

        Child child = new Child(id, fname, lname, notes, authenticationService.getCurrentUserId(), birthday);
        return child;
    }

    private boolean isValid() {
        // TODO check validations

        Child child = getChild();
        
        if (!Validator.isNameValid(child.getFname())) {
            etFname.setError("Name Error");
            etFname.requestFocus();
            return false;
        }


        return true;
    }
}