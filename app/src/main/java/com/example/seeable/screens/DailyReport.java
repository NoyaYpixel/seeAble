package com.example.seeable.screens;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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


        Boolean fi = IsChecked(radioGroup5, R.id.rbtnYes5, R.id.rbtnNo5);
        if (fi == null) {
            Toast.makeText(this, "asdads", Toast.LENGTH_SHORT).show();
            return;
        }

        Boolean fo = IsChecked(radioGroup4, R.id.rbtnYes4, R.id.rbtnNo4);
        if (fo == null) {
            Toast.makeText(this, "asdads", Toast.LENGTH_SHORT).show();
            return;
        }

        Boolean th = IsChecked(radioGroup1, R.id.rbtnYes3, R.id.rbtnNo3);
        if (th == null) {
            Toast.makeText(this, "asdads", Toast.LENGTH_SHORT).show();
            return;
        }

        Boolean two = IsChecked(radioGroup2, R.id.rbtnYes2, R.id.rbtnNo2);
        if (two == null) {
            Toast.makeText(this, "asdads", Toast.LENGTH_SHORT).show();
            return;
        }

        Boolean one = IsChecked(radioGroup3, R.id.rbtnYes1, R.id.rbtnNo1);
        if (one == null) {
            Toast.makeText(this, "asdads", Toast.LENGTH_SHORT).show();
            return;
        }


        RadioButton rbtnYes1 = findViewById(R.id.rbtnYes1);  // כן
        RadioButton rbtnNo1 = findViewById(R.id.rbtnNo1);  // לא

        RadioButton rbtnYes2 = findViewById(R.id.rbtnYes2);  // כן
        RadioButton rbtnNo2 = findViewById(R.id.rbtnNo2);  // לא

        RadioButton rbtnYes3 = findViewById(R.id.rbtnYes3);  // כן
        RadioButton rbtnNo3 = findViewById(R.id.rbtnNo3);  // לא

        RadioButton rbtnYes4 = findViewById(R.id.rbtnYes4);  // כן
        RadioButton rbtnNo4 = findViewById(R.id.rbtnNo4);  // לא

        RadioButton rbtnYes5 = findViewById(R.id.rbtnYes5);  // כן
        RadioButton rbtnNo5 = findViewById(R.id.rbtnNo5);  // לא

    }

    private Boolean IsChecked(RadioGroup radioGroup, int yesID, int noID) {
        if (radioGroup.getCheckedRadioButtonId() == yesID) {
            return true;
        }
        else if (radioGroup.getCheckedRadioButtonId() == noID) {
            return false;
        }
        radioGroup.requestFocus();
        return null;
    }
}