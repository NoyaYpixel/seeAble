package com.example.seeable.screens;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.seeable.R;
import com.example.seeable.model.User;
import com.example.seeable.services.AuthenticationService;
import com.example.seeable.utils.SharedPreferencesUtil;

public class ShowReport extends AppCompatActivity {
    TextView tvIsArrived, tvIsGood, tvIsAte, tvIsDrank, tvIsSlept;
    TextView tvBoolean1, tvBoolean2, tvBoolean3, tvBoolean4, tvBoolean5;
    AuthenticationService authenticationService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Init();
    }

    private void Init() {
        tvIsArrived=findViewById(R.id.tvIsArrived);
        tvIsGood=findViewById(R.id.tvIsGood);
        tvIsAte=findViewById(R.id.tvIsAte);
        tvIsDrank=findViewById(R.id.tvIsDrank);
        tvIsSlept=findViewById(R.id.tvIsSlept);
        tvBoolean1=findViewById(R.id.tvBoolean1);
        tvBoolean2=findViewById(R.id.tvBoolean2);
        tvBoolean3=findViewById(R.id.tvBoolean3);
        tvBoolean4=findViewById(R.id.tvBoolean4);
        tvBoolean5=findViewById(R.id.tvBoolean5);
    }
}