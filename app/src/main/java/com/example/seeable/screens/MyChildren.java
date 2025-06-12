package com.example.seeable.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeable.R;
import com.example.seeable.adapters.ChildAdapter;
import com.example.seeable.model.Child;
import com.example.seeable.services.AuthenticationService;
import com.example.seeable.services.DatabaseService;

import java.util.List;

public class MyChildren extends MyBaseActivity {

    private static final String TAG = "MyChildren";

    DatabaseService databaseService;

    RecyclerView recyclerView;
    ChildAdapter childAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_children);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseService = DatabaseService.getInstance();
        recyclerView = findViewById(R.id.rv_children);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        childAdapter = new ChildAdapter(new ChildAdapter.OnChildClick() {
            @Override
            public void OnClick(Child child) {
                Intent intent = new Intent(MyChildren.this, ChildReports.class);
                intent.putExtra("child", child);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(childAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();

        databaseService.getChildren(new DatabaseService.DatabaseCallback<List<Child>>() {
            @Override
            public void onCompleted(List<Child> children) {
                children.removeIf(child -> !child.getParentId().equals(AuthenticationService.getInstance().getCurrentUserId()));
                childAdapter.setChildList(children);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });

    }
}