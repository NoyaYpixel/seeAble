package com.example.seeable.screens;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seeable.R;
import com.example.seeable.model.User;
import com.example.seeable.services.AuthenticationService;
import com.example.seeable.utils.SharedPreferencesUtil;

public class MyBaseActivity extends AppCompatActivity {
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        User user = SharedPreferencesUtil.getUser(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.signOut) {
            AuthenticationService.getInstance().signOut();
            SharedPreferencesUtil.signOutUser(this);
            Intent go = new Intent(this, MainActivity.class);
            go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(go);
            Toast.makeText(this, "התנתקת בהצלחה", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.signIn) {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.logIn)
        {
            Intent go = new Intent(this, Login.class);
            startActivity(go);
            return true;
        }
        if (item.getItemId() == R.id.homePage)
        {
            Intent go = new Intent(this, HomePage.class);
            startActivity(go);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
