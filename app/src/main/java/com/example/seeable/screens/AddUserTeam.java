package com.example.seeable.screens;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeable.R;
import com.example.seeable.model.User;
import com.example.seeable.adapters.UsersAdapter;
import com.example.seeable.services.DatabaseService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddUserTeam extends AppCompatActivity {
    RecyclerView usersRecyclerView;
    UsersAdapter usersAdapter;

    DatabaseService databaseService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_user_team);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseService = DatabaseService.getInstance();

        usersRecyclerView = findViewById(R.id.usersRecyclerView);
        usersAdapter = new UsersAdapter(new UsersAdapter.OnUserListener() {
            @Override
            public void onSwitch(boolean isChecked) {
                //TODO
            }
        });

        usersRecyclerView.setAdapter(usersAdapter);


        databaseService.getUsers(new DatabaseService.DatabaseCallback<List<User>>() {
            @Override
            public void onCompleted(List<User> users) {
                usersAdapter.setUsers(users);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });

    }


    // הצגת דיאלוג שבו המנהל יוכל לבחור את סוג המשתמש (צוות או הורה)
    private void showUserTypeDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select User Type");

        // אפשרויות: צוות או הורה
        String[] userTypes = {"Team", "Parent"};

        builder.setItems(userTypes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedType = userTypes[which];

                // עדכון ב-Firebase
                updateUserType(user.getId(), selectedType);

                Toast.makeText(getApplicationContext(), "User type updated to " + selectedType, Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    // עדכון סוג המשתמש ב-Firebase
    private void updateUserType(String userId, String userType) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        userRef.child("userType").setValue(userType);  // עדכון שדה userType
    }

}