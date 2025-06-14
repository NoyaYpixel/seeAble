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

import java.util.Objects;

public class HomePage extends MyBaseActivity implements View.OnClickListener {

    User user;
    Button btnAddC, btnAddTM, btnEtUser, btnChildReport, btnChildrenList,btnShowR ;

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

    @Override
    protected void onResume() {
        super.onResume();
        this.user = SharedPreferencesUtil.getUser(this);

        if (Objects.equals(user.getPosition(), User.Position.Manager.getType())){
            ((TextView)findViewById(R.id.textView3)).setText("היי למנהל.ת "+ this.user.getFname() + " " + this.user.getLname()+ "!");
        }
        else if (Objects.equals(user.getPosition(), User.Position.Team.getType())) {
            ((TextView)findViewById(R.id.textView3)).setText("היי לאיש צוות "+ this.user.getFname() + " " + this.user.getLname()+ "!");
        }
        else {
            ((TextView) findViewById(R.id.textView3)).setText("היי להורה " + this.user.getFname() + " " + this.user.getLname() + "!");
        }

        if (Objects.equals(user.getPosition(), User.Position.Manager.getType())) {
            btnAddTM.setVisibility(View.VISIBLE);
            btnChildReport.setVisibility(View.VISIBLE);
            btnAddC.setVisibility(View.GONE);
            btnChildrenList.setVisibility(View.VISIBLE);
            btnShowR.setVisibility(View.GONE);
        } else if (Objects.equals(user.getPosition(), User.Position.Team.getType())) {
            btnAddTM.setVisibility(View.GONE);
            btnChildReport.setVisibility(View.VISIBLE);
            btnAddC.setVisibility(View.GONE);
            btnChildrenList.setVisibility(View.VISIBLE);
            btnShowR.setVisibility(View.GONE);
        } else if (Objects.equals(user.getPosition(), User.Position.Normal.getType())) {
            btnAddTM.setVisibility(View.GONE);
            btnChildReport.setVisibility(View.GONE);
            btnAddC.setVisibility(View.VISIBLE);
            btnChildrenList.setVisibility(View.GONE);
            btnShowR.setVisibility(View.VISIBLE);
        }
    }

    private void Init() {


        btnAddC = findViewById(R.id.btnAddC);
        btnAddC.setOnClickListener(this);
        btnAddTM = findViewById(R.id.btnAddTM);
        btnAddTM.setOnClickListener(this);
        btnChildReport = findViewById(R.id.btnChildReport);
        btnChildReport.setOnClickListener(this);
        btnChildrenList = findViewById(R.id.btnChildrenList);
        btnChildrenList.setOnClickListener(this);
        btnShowR = findViewById(R.id.btnShowR);
        btnShowR.setOnClickListener(this);
        btnEtUser = findViewById(R.id.btnEtUser);
        btnEtUser.setOnClickListener(this);


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
        if(view==btnChildrenList)
        {
            Intent go=new Intent(getApplicationContext(), ChildInfo.class);
            startActivity(go);
        }
        if(view==btnShowR)
        {
            Intent go=new Intent(getApplicationContext(), MyChildren.class);
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