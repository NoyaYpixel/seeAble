package com.example.seeable.screens;

import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import android.content.Intent;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.seeable.R;
import com.example.seeable.model.Child;
import com.example.seeable.model.MyDate;
import com.example.seeable.model.Report;
import com.example.seeable.services.DatabaseService;
import com.example.seeable.utils.SharedPreferencesUtil;

public class DailyReport extends AppCompatActivity implements View.OnClickListener {

    private Child child;
    private Button btnDailyReportUpdating;
    private TextView tvChildName;

    DatabaseService databaseService;

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

        databaseService = DatabaseService.getInstance();

        child = (Child) getIntent().getSerializableExtra("child");

        if (child == null) {
            finish();
            return;
        }

        btnDailyReportUpdating = findViewById(R.id.btnDailyReportUpdating);
        btnDailyReportUpdating.setOnClickListener(this);

        tvChildName = findViewById(R.id.tvChildName);

        tvChildName.setText(child.getFname() + " " + child.getLname());

        // show correctly the existing report (if exit) in this page

    }

    @Nullable
    private Boolean isChecked(RadioGroup radioGroup, int yesID, int noID) {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        if (checkedId == yesID) return true;
        if (checkedId == noID) return false;
        return null;
    }


    private void sendDailyReport(Report report) {
        databaseService.addDailyReport(report, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {

            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnDailyReportUpdating) {
            RadioGroup radioGroup1 = findViewById(R.id.radioGroup1);
            RadioGroup radioGroup2 = findViewById(R.id.radioGroup2);
            RadioGroup radioGroup3 = findViewById(R.id.radioGroup3);
            RadioGroup radioGroup4 = findViewById(R.id.radioGroup4);
            RadioGroup radioGroup5 = findViewById(R.id.radioGroup5);

            Boolean isArrived =     isChecked(radioGroup1, R.id.rbtnYes1, R.id.rbtnNo1);
            Boolean isFeelingGood = isChecked(radioGroup2, R.id.rbtnYes2, R.id.rbtnNo2);
            Boolean isEatMorning =  isChecked(radioGroup3, R.id.rbtnYes3, R.id.rbtnNo3);
            Boolean isDrank =       isChecked(radioGroup4, R.id.rbtnYes4, R.id.rbtnNo4);
            Boolean isSlept =       isChecked(radioGroup5, R.id.rbtnYes5, R.id.rbtnNo5);

            Report report = new Report(child.getId(), MyDate.now(), isArrived, isFeelingGood, isEatMorning, isDrank, isSlept);

            Log.d("!!!!!!!!!!!!!!!", report.toString());

            sendDailyReport(report);
            Bitmap bitmap = captureScreenAsBitmap();
            byte[] byteArray = bitmapToByteArray(bitmap);

            Intent intent = new Intent(DailyReport.this, ShowReport.class);
            intent.putExtra("report_image", byteArray);
            intent.putExtra("Report", report);
            startActivity(intent);
            return;
        }
    }
    private Bitmap captureScreenAsBitmap() {
        View rootView = getWindow().getDecorView().getRootView();
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}