package com.example.seeable.screens;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.seeable.R;
import com.example.seeable.model.Report;
import com.example.seeable.model.User;
import com.example.seeable.services.AuthenticationService;
import com.example.seeable.utils.SharedPreferencesUtil;

public class ShowReport extends MyBaseActivity {
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
//        byte[] byteArray = getIntent().getByteArrayExtra("report_image");
//
//        if (byteArray != null) {
//            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//            ImageView imageView = findViewById(R.id.imageViewReport);
//            imageView.setImageBitmap(bitmap);
//        }
        Init();
        Report report = (Report) getIntent().getSerializableExtra("Report");
        assert report != null;
        tvBoolean1.setText(getStatus(report.getArrived()));
        tvBoolean2.setText(getStatus(report.getFeelingGood()));
        tvBoolean3.setText(getStatus(report.getEatMorning()));
        tvBoolean4.setText(getStatus(report.getDrank()));
        tvBoolean5.setText(getStatus(report.getSlept()));
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
    private String getStatus(Boolean flag){
        if (flag == null) {
            return "עדיין לא סומן";
        }
        else if (flag==true)
        {
            return "כן";
        }
        else if (flag==false)
        {
            return "לא";
        }
        return "עדיין לא סומן";
    }
}
