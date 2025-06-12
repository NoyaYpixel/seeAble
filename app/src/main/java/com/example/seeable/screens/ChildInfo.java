package com.example.seeable.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.example.seeable.model.Comment;
import com.example.seeable.services.DatabaseService;

import java.util.List;

public class ChildInfo extends MyBaseActivity implements View.OnClickListener {
    private static final String TAG = "ChildInfo";
    ImageButton img_btn_trash;
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
        /*img_btn_trash = findViewById(R.id.img_btn_trash);
        img_btn_trash.setOnClickListener(this);*/
        childAdapter = new ChildInfoAdapter();
        childAdapter.setOnChildDeleteClickListener((child, position) -> {
            databaseService.deleteChild(child.getId(), new DatabaseService.DatabaseCallback<Void>() {
                @Override
                public void onCompleted(Void result) {
                    runOnUiThread(() -> {
                        childAdapter.removeChild(position);
                    });
                }

                @Override
                public void onFailed(Exception e) {
                    runOnUiThread(() -> {
                        Toast.makeText(ChildInfo.this, "שגיאה במחיקה", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
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

    @Override
    public void onClick(View v) {
        //if (v==imageButton)
        {
       //     databaseService.deleteChild();
        }

    }
}