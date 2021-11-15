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

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

import com.amplifyframework.datastore.generated.model.Status;
import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.amplifyframework.datastore.generated.model.Team;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.concurrent.atomic.AtomicBoolean;


public class MyTasks extends AppCompatActivity {
    private static final String TAG ="MyTasks" ;
    ChipNavigationBar chipNavigationBar;
TextView user;
TextView mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
        String username = sharedPreferences.getString("username",null);
        String email = sharedPreferences.getString("email",null);
        user=(TextView)findViewById(R.id.username);
        mail=(TextView)findViewById(R.id.email);
        user.setText(username);
        mail.setText(email);

        /**AWS stuff**/
        createTeams();

        /**end here**/

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

                case R.id.item2: {
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

    private void createTeams(){

        Team todo1 = Team.builder()
                .name("Team 1").id("1")
                .build();

        Amplify.API.mutate(
                ModelMutation.create(todo1),
                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
        Team todo2 = Team.builder()
                .name("Team 2").id("2")
                .build();

        Amplify.API.mutate(
                ModelMutation.create(todo2),
                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
        Team todo3 = Team.builder()
                .name("Team 3").id("3")
                .build();

        Amplify.API.mutate(
                ModelMutation.create(todo3),
                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
    }
    public void add(View view) {
        Intent intent=new Intent(MyTasks.this, AddTask.class);
        startActivity(intent);
    }

    public void all(View view) {
        Intent intent=new Intent(MyTasks.this,AllTasks.class);
        startActivity(intent);
    }


    public void setting(View view) {
        Intent i=new Intent(MyTasks.this,SettingsPage.class);
        startActivity(i);

    }
    /***AWS**/


    public void go(View view) {

    }

    /********/
}