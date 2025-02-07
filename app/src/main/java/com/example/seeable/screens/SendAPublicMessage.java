package com.example.seeable.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.seeable.R;
import com.example.seeable.model.Message;
import com.example.seeable.model.User;
import com.example.seeable.services.AuthenticationService;
import com.example.seeable.services.DatabaseService;

import java.util.Date;

public class SendAPublicMessage extends AppCompatActivity implements View.OnClickListener {
    EditText etMessageContent;
    Spinner spnWhoReceive;
    Button btnSendMessage;
    private AuthenticationService authenticationService;
    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_apublic_message);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Init();
    }

    private void Init() {
        etMessageContent = findViewById(R.id.et_content);
        spnWhoReceive = findViewById(R.id.spinner_message);
        btnSendMessage = findViewById(R.id.btnSendPublicM);

        authenticationService = AuthenticationService.getInstance();
        databaseService = DatabaseService.getInstance();

        btnSendMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnSendMessage.getId()) {
            String content = etMessageContent.getText().toString();
            String whoReceive = spnWhoReceive.getSelectedItem().toString();
            String id = databaseService.getNewMessageId();
            Message message = new Message(id, content, authenticationService.getCurrentUserId(), whoReceive, new Date());

            databaseService.createNewMessage(message, new DatabaseService.DatabaseCallback<Object>() {
                @Override
                public void onCompleted(Object object) {
                    etMessageContent.setText("");
                    Toast.makeText(SendAPublicMessage.this, "message send successfully!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(Exception e) {
                    Log.e("SendAPublicMessage", e.getMessage().toString());
                }
            });

        }
    }
}