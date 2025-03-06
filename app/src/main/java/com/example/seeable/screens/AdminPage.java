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
import com.example.seeable.services.AuthenticationService;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
            Intent go=new Intent(getApplicationContext(), AllUsers.class);
            startActivity(go);
        }
        if(view==btnAddComment)
        {
            Intent go=new Intent(getApplicationContext(), SendAPublicMessage.class);
            startActivity(go);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu from the XML resource
        getMenuInflater().inflate(R.menu.menu_main, menu);  // יוצר את התפריט עם ה-XML שיצרנו
        return true;  // מחזיר true כדי שהתפריט יוצג
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // טיפול במצב של בחירת פריט בתפריט
        if (item.getItemId()==R.id.signOut)
            {
                AuthenticationService.getInstance().signOut();
                Intent go = new Intent(this,MainActivity.class);
                startActivity(go);
                Toast.makeText(this, "התנתקת בהצלחה :)", Toast.LENGTH_SHORT).show();
                return true;
            }
        return onOptionsItemSelected(item);
    }
}