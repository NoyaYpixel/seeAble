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
import com.example.seeable.adapters.ReportAdapter;
import com.example.seeable.model.Child;
import com.example.seeable.model.Report;
import com.example.seeable.services.DatabaseService;

public class ChildReports extends MyBaseActivity {

    DatabaseService databaseService;
    Child child;

    ReportAdapter reportAdapter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_reports);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseService = DatabaseService.getInstance();

        recyclerView = findViewById(R.id.rv_child_reports);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        child = (Child) getIntent().getSerializableExtra("child");

        reportAdapter = new ReportAdapter(new ReportAdapter.OnReportClick() {
            @Override
            public void OnClick(Report report) {
                Intent intent = new Intent(ChildReports.this, ShowReport.class);
                intent.putExtra("Report", report);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(reportAdapter);

        reportAdapter.setReportList(child.getDailyReports());

    }
}