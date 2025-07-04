package com.example.seeable.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeable.R;
import com.example.seeable.adapters.ChildAdapter;
import com.example.seeable.adapters.UsersAdapter;
import com.example.seeable.model.Child;
import com.example.seeable.services.DatabaseService;
import com.example.seeable.utils.SharedPreferencesUtil;

import java.util.List;

public class ChildrenList extends MyBaseActivity {

    private static final String TAG = "ChildrenList";

    DatabaseService databaseService;

    RecyclerView recyclerView;
    ChildAdapter childAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_children_list);
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
                Intent intent = new Intent(ChildrenList.this, DailyReport.class);
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
                childAdapter.setChildList(children);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });

    }
}