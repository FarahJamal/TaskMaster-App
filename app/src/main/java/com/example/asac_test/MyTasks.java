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
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

import com.amplifyframework.datastore.generated.model.Status;
import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class MyTasks extends AppCompatActivity {
    private static final String TAG ="MyTasks" ;
    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        /**AWS stuff**/

        configureAmplify();

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
        //     Amplify.DataStore.save(item,
//    success -> Log.i("Tutorial", "Saved item: " + success.item().getTitle()),
//    error -> Log.e("Tutorial", "Could not save item to DataStore", error)
//            );

//        Amplify.DataStore.query(TaskMaster.class,
//                tasks -> {
//                    while (tasks.hasNext()) {
//                        TaskMaster task = tasks.next();
//
//                        Log.i("Tutorial", "==== Todo ====");
//                        Log.i("Tutorial", "Name: " + task.getTitle());
//
//                        if (task.getStatus() != null) {
//                            Log.i("Tutorial", "Priority: " + task.getStatus().toString());
//                        }
//
//                        if (task.getBody() != null) {
//                            Log.i("Tutorial", "CompletedAt: " + task.getBody().toString());
//                        }
//                    }
//                },
//                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
//        );
           }
//    TaskMaster item = TaskMaster.builder()
//            .title("Build Android application")
//            .status(Status.IN_PROGRESS)
//            .body("complete building this App with Amplify and AWS ... ")
//            .build();


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
    /***AWS**/
    private void configureAmplify() {
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException failure) {
            Log.e("Tutorial", "Could not initialize Amplify", failure);
        }
        Amplify.DataStore.observe(TaskMaster.class,
                started -> Log.i("Tutorial", "Observation began."),
                change -> Log.i("Tutorial", change.item().toString()),
                failure -> Log.e("Tutorial", "Observation failed.", failure),
                () -> Log.i("Tutorial", "Observation complete.")
        );
    }

    /********/
}