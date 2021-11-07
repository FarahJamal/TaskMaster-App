package com.example.asac_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class MyTasks extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);
        chipNavigationBar=findViewById(R.id.chip);
        chipNavigationBar.setOnItemSelectedListener(i -> {
            Log.v("id value => ",i+"");

            switch (i){
                case R.id.item0:
                {
                    Log.v("id => ",i+"");

                    Intent activity1=new Intent(getBaseContext(),MyTasks.class);
                    startActivity(activity1);
                }

                case R.id.item1: {
                    Log.v("id => ",i+"");

                    Intent activity2 = new Intent(getBaseContext(), AddTask.class);
                    startActivity(activity2);
                }

                case 2131230987: {
                    Log.v("id => ",i+"");

                    Intent activity3 = new Intent(getBaseContext(), SettingsPage.class);
                    startActivity(activity3);
                }

                case R.id.item3: {
                    Log.v("id => ",i+"");

                    Intent activity4 = new Intent(getBaseContext(), AddTask.class);
                    startActivity(activity4);
                }
            }
        });
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