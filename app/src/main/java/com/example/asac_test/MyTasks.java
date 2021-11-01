package com.example.asac_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

    }


    public void add(View view) {
        Intent intent=new Intent(MyTasks.this, AddTask.class);
        startActivity(intent);
    }

    public void all(View view) {
        Intent intent=new Intent(MyTasks.this,AllTasks.class);
        startActivity(intent);
    }
    @Override
    protected void onResume() {

        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String user = prefs.getString("username","guest");
        String mail = prefs.getString("email",null);
        TextView username=(TextView)findViewById(R.id.username);
        TextView email=(TextView)findViewById(R.id.email);
        username.setText("Hello, "+user);
        email.setText(mail);
    }

    public void setting(View view) {
        Intent i=new Intent(MyTasks.this,SettingsPage.class);
        startActivity(i);

    }
}