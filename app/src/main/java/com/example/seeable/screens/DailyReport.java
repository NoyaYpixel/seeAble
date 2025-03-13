package com.example.seeable.screens;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.seeable.R;

public class DailyReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daily_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Init();
    }

    private void Init() {
        RadioGroup radioGroup1 = findViewById(R.id.radioGroup1);
        RadioGroup radioGroup2 = findViewById(R.id.radioGroup2);
        RadioGroup radioGroup3 = findViewById(R.id.radioGroup3);
        RadioGroup radioGroup4 = findViewById(R.id.radioGroup4);
        RadioGroup radioGroup5 = findViewById(R.id.radioGroup5);

        RadioButton radioButton4 = findViewById(R.id.radioButton4);  // כן
        RadioButton radioButton5 = findViewById(R.id.radioButton5);  // לא

        RadioButton radioButton6 = findViewById(R.id.radioButton6);  // כן
        RadioButton radioButton7 = findViewById(R.id.radioButton7);  // לא

        RadioButton radioButton8 = findViewById(R.id.radioButton8);  // כן
        RadioButton radioButton9 = findViewById(R.id.radioButton9);  // לא

        RadioButton radioButton10 = findViewById(R.id.radioButton10);  // כן
        RadioButton radioButton11 = findViewById(R.id.radioButton11);  // לא

        RadioButton radioButton14 = findViewById(R.id.radioButton14);  // כן
        RadioButton radioButton15 = findViewById(R.id.radioButton15);  // לא

    }
}