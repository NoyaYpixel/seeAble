package com.example.seeable.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.seeable.R;

public class AdminPage extends AppCompatActivity implements View.OnClickListener {
Button btnAddChild, btnAddTeamMember, btnAddComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Init();
    }

    private void Init() {
        btnAddChild=findViewById(R.id.btnAddChild);
        btnAddChild.setOnClickListener(this);
        btnAddTeamMember=findViewById(R.id.btnAddTeamMember);
        btnAddTeamMember.setOnClickListener(this);
        btnAddComment=findViewById(R.id.btnSendMessage);
        btnAddComment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==btnAddChild)
        {
            Intent go=new Intent(getApplicationContext(), AddChild.class);
            startActivity(go);
        }
        if(view==btnAddTeamMember)
        {
            Intent go=new Intent(getApplicationContext(), AddUserTeam.class);
            startActivity(go);
        }
        if(view==btnAddComment)
        {
            Intent go=new Intent(getApplicationContext(), SendAPublicMessage.class);
            startActivity(go);
        }
    }
}