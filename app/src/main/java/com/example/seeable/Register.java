package com.example.seeable;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.seeable.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText etfname, etlname, etEmail, etPhone, etPassword;
    Button btnReg;
    String fname, lname, phone, email, password;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Init();
    }

    private void Init() {
        etfname=findViewById(R.id.etFname);
        etlname=findViewById(R.id.etLname);
        etPhone=findViewById(R.id.etPhone);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnReg=findViewById(R.id.btnReg);
        btnReg.setOnClickListener(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onClick(View v) {
        fname=etfname.getText().toString();
        lname=etlname.getText().toString();
        phone=etPhone.getText().toString();
        email=etEmail.getText().toString();
        password=etPassword.getText().toString();

        //check if registration is valid
        Boolean isValid=true;
        if (fname.length()<2){
            Toast.makeText(Register.this,"שם פרטי קצר מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if (lname.length()<2){
            Toast.makeText(Register.this,"שם משפחה קצר מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if (phone.length()<9||phone.length()>10){
            Toast.makeText(Register.this,"מספר הטלפון לא תקין", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if (!email.contains("@")){
            Toast.makeText(Register.this,"כתובת האימייל לא תקינה", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if(password.length()<6){
            Toast.makeText(Register.this,"הסיסמה קצרה מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if(password.length()>20){
            Toast.makeText(Register.this,"הסיסמה ארוכה מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }

        if (isValid==true){

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                FirebaseUser fireuser = mAuth.getCurrentUser();
                                User newUser=new User(fireuser.getUid(), fname, lname, phone, email, password);
                                myRef.child(fireuser.getUid()).setValue(newUser);
                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                editor.putString("email", email);
                                editor.putString("password", password);

                                editor.commit();
                                Intent goLog=new Intent(getApplicationContext(), MainActivity.class);
                                goLog.setAction(Intent.ACTION_SEND);
                                goLog.putExtra(Intent.EXTRA_TEXT,"Sharing data from my App");
                                goLog.setType("text/plain");
                                startActivity(goLog);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Register.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                            // ...
                        }
                    });
        }


    }
}
