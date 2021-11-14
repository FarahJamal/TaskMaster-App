package com.example.asac_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.asac_test.DataBase.AppDatabase;
import com.example.asac_test.Entity.TaskEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllTasks extends AppCompatActivity {

    Handler handler;
    List<TaskEntity> allTasks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent i=new Intent(AllTasks.this,MyTasks.class);
                startActivity(i);
                Toast.makeText(this,"Back button pressed!",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        getTasks();

        RecyclerView recyclerView = findViewById(R.id.recTask);

        TaskAdapter newAdapter = new TaskAdapter( getApplicationContext(), (ArrayList<TaskEntity>) allTasks);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(newAdapter);

        handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                newAdapter.setTaskOGList(allTasks);
                Log.i("Async", allTasks.toString());
                recyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

    }

    private void getTasks() {
        List<TaskMaster> listOfTasks = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
        String settingsTeamID = sharedPreferences.getString("teamName",null);
        Log.i("TeamID", "Settings Team ID ===> " + settingsTeamID);

        if (settingsTeamID == null) {

            Amplify.API.query(
                    ModelQuery.list(TaskMaster.class),
                    response -> {
                        for (TaskMaster task : response.getData()) {
                            listOfTasks.add(task);
                        }
                        Collections.sort(listOfTasks, new Comparator<TaskMaster>() {
                            @Override
                            public int compare(TaskMaster task, TaskMaster t1) {
                                return Long.compare(task.getCreatedAt().toDate().getTime(), t1.getCreatedAt().toDate().getTime());
                            }
                        });

                        for (TaskMaster task : listOfTasks
                        ) {
                            allTasks.add(new TaskEntity(task.getTitle(), task.getBody(), task.getStatus().name(),task.getTeamId()));
                        }

                        handler.sendEmptyMessage(1);
                    },
                    error -> Log.e("MyAmplifyApp", "Query failure", error)
            );
        } else {
            Log.v("inside elese =>",settingsTeamID);
            Amplify.API.query(
                    ModelQuery.list(TaskMaster.class, TaskMaster.TEAM_ID.contains(settingsTeamID)),
                    response -> {
                        for (TaskMaster task : response.getData()) {
                            listOfTasks.add(task);
                        }
                        Collections.sort(listOfTasks, new Comparator<TaskMaster>() {
                            @Override
                            public int compare(TaskMaster task, TaskMaster t1) {
                                return Long.compare(task.getCreatedAt().toDate().getTime(), t1.getCreatedAt().toDate().getTime());
                            }
                        });

                        for (TaskMaster task : listOfTasks
                        ) {
                            allTasks.add(new TaskEntity(task.getTitle(), task.getBody(), task.getStatus().name(),task.getTeamId()));
                        }

                        handler.sendEmptyMessage(1);
                    },
                    error -> Log.e("MyAmplifyApp", "Query failure", error)
            );


        }


    }
}