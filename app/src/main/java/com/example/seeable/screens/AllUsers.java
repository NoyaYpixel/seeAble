package com.example.seeable.screens;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeable.R;
import com.example.seeable.adapters.UsersAdapter;
import com.example.seeable.model.User;
import com.example.seeable.services.DatabaseService;

import java.util.List;

public class AllUsers extends MyBaseActivity {
    private static final String TAG = "AllUsers";

    DatabaseService databaseService;

    RecyclerView recyclerView;
    UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_users);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseService = DatabaseService.getInstance();
        recyclerView = findViewById(R.id.rv_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersAdapter = new UsersAdapter(new UsersAdapter.OnUserListener() {
            @Override
            public void onSwitch(User user, boolean isChecked) {
                Log.d(TAG, "All Users");
                if (isChecked) {
                    user.setPosition(User.Position.Team.getType());
                } else {
                    user.setPosition(null);
                }
                databaseService.createNewUser(user, new DatabaseService.DatabaseCallback<Object>() {
                    @Override
                    public void onCompleted(Object object) {

                    }

                    @Override
                    public void onFailed(Exception e) {

                    }
                });
            }
        });
        recyclerView.setAdapter(usersAdapter);

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
}