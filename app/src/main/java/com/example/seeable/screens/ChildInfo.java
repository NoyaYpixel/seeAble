package com.example.seeable.screens;

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
import com.example.seeable.adapters.ChildInfoAdapter;
import com.example.seeable.model.Child;
import com.example.seeable.services.DatabaseService;

import java.util.List;

public class ChildInfo extends AppCompatActivity {
    private static final String TAG = "ChildInfo";

    DatabaseService databaseService;

    RecyclerView recyclerView;
    ChildInfoAdapter childAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseService = DatabaseService.getInstance();
        recyclerView = findViewById(R.id.rv_childInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        childAdapter = new ChildInfoAdapter();
        recyclerView.setAdapter(childAdapter);

        databaseService.getChildren(new DatabaseService.DatabaseCallback<List<Child>>() {
            @Override
            public void onCompleted(List<Child> children) {
                childAdapter.setChildList(children);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
}