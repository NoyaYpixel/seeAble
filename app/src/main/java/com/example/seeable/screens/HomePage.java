package com.example.seeable.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.seeable.R;
import com.example.seeable.model.User;
import com.example.seeable.services.AuthenticationService;
import com.example.seeable.utils.SharedPreferencesUtil;

public class HomePage extends MyBaseActivity implements View.OnClickListener {

    User user;
    Button btnAddC, btnAddSM, btnAddTM, btnChildInfo, btnEtUser, btnChildReport ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Init();
    }
    private void Init() {
        this.user = SharedPreferencesUtil.getUser(this);

        if (user.isAdmin()){
            ((TextView)findViewById(R.id.textView3)).setText("היי למנהל "+ this.user.getFname() + " " + this.user.getLname()+ "!");
        }
        else {
            ((TextView) findViewById(R.id.textView3)).setText("היי למשתמש.ת " + this.user.getFname() + " " + this.user.getLname() + "!");
        }

        btnAddC = findViewById(R.id.btnAddC);
        btnAddC.setOnClickListener(this);
        btnAddSM = findViewById(R.id.btnAddSM);
        btnAddSM.setOnClickListener(this);
        btnAddTM = findViewById(R.id.btnAddTM);
        btnAddTM.setOnClickListener(this);
        btnChildReport = findViewById(R.id.btnChildReport);
        btnChildReport.setOnClickListener(this);
        btnEtUser = findViewById(R.id.btnEtUser);
        btnEtUser.setOnClickListener(this);
        btnChildInfo = findViewById(R.id.btnChildInfo);
        btnChildInfo.setOnClickListener(this);

        if (user.isAdmin()) {
            btnAddTM.setVisibility(View.VISIBLE);
            btnAddSM.setVisibility(View.VISIBLE);
            btnChildReport.setVisibility(View.VISIBLE);
        } else {
            btnAddTM.setVisibility(View.GONE);
            btnAddSM.setVisibility(View.GONE);
            btnChildReport.setVisibility(View.VISIBLE);
        }
    }
    public void onClick(View view) {
        if(view==btnAddC)
        {
            Intent go=new Intent(getApplicationContext(), AddChild.class);
            startActivity(go);
        }
        if(view==btnAddTM)
        {
            Intent go=new Intent(getApplicationContext(), AllUsers.class);
            startActivity(go);
        }
        if(view==btnAddSM)
        {
            Intent go=new Intent(getApplicationContext(), SendAPublicMessage.class);
            startActivity(go);
        }
        if(view==btnEtUser)
        {
            Intent go=new Intent(getApplicationContext(), EditUser.class);
            startActivity(go);
        }
        if(view==btnChildReport)
        {
            Intent go=new Intent(getApplicationContext(), ChildrenList.class);
            startActivity(go);
        }
        if(view==btnChildInfo)
        {
            Intent go=new Intent(getApplicationContext(), ChildInfo.class);
            startActivity(go);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_signOut) {
            AuthenticationService.getInstance().signOut();
            SharedPreferencesUtil.signOutUser(this);
            Intent go = new Intent(this, MainActivity.class);
            go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(go);
            Toast.makeText(this, "התנתקת בהצלחה", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}