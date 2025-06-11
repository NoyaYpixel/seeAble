package com.example.seeable.screens;

import android.os.Bundle;
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

public class EditUser extends AppCompatActivity {

    EditText etFN, etLN, etP;
    DatabaseService databaseService;
    String userID;
    User user;
    Button btnUpdateUser;

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

        databaseService = DatabaseService.getInstance();
        Init();
        userID = AuthenticationService.getInstance().getCurrentUserId();

        databaseService.getUser(userID, new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User user) {
                setView(user);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });

    }

    private void Init() {
        etFN = findViewById(R.id.etFN);
        etLN = findViewById(R.id.etLN);
        etP = findViewById(R.id.etP);

        btnUpdateUser = findViewById(R.id.btnUpdateUser);
        btnUpdateUser.setOnClickListener(v -> updateUser());
    }

    private void setView(User user) {
        this.user = user;

        etFN.setText(user.getFname());
        etLN.setText(user.getLname());
        etP.setText(user.getPhone());
    }

    private void updateUser() {
        // קריאת הערכים מהשדות
        String newFirstName = etFN.getText().toString().trim();
        String newLastName = etLN.getText().toString().trim();
        String newPhone = etP.getText().toString().trim();

        // עדכון האובייקט
        user.setFname(newFirstName);
        user.setLname(newLastName);
        user.setPhone(newPhone);

        // קריאה לשירות לעדכון במסד הנתונים
        databaseService.updateUser(user, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void unused) {
                // הצלחה
                Toast.makeText(EditUser.this, "הפרטים עודכנו בהצלחה!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Exception e) {
                // כישלון בעדכון
                Toast.makeText(EditUser.this, "שגיאה בעדכון הפרטים: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }


            // TODO update user in DB by clicking a button
        });  // <-- סוגר קריאה ל updateUser עם callback}
    }
}