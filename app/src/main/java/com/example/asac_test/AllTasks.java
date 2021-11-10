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

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.asac_test.DataBase.AppDatabase;
import com.example.asac_test.Entity.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class AllTasks extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
//        ArrayList<Task>taskData=new ArrayList<>();
//        taskData.add(new Task("study","Study for mock interviews","In Progress"));
//        taskData.add(new Task("read","Finish the reading 28","In Progress"));
//        taskData.add(new Task("fun","Watch movie","assigned"));
//        taskData.add(new Task("coding","Finished code-challenge","Completed"));


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {

        super.onStart();
        List<TaskEntity> taskData = AppDatabase.getInstance(getApplicationContext()).taskDAO().getAll();
        List<TaskMaster> taskList = new ArrayList<TaskMaster>();
        List<Team> teamList = new ArrayList<Team>();

        RecyclerView allStudentRecyclerView = findViewById(R.id.recTask);
        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                allStudentRecyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Team.class),
                response -> {
                    for (Team tema : response.getData()) {
//                        Log.i("MyAmplifyApp", tema.getName());
//                        Log.i("MyAmplifyApp", tema.getId());
                        teamList.add(tema);
                    }
                    for (int i = 0; i < teamList.size(); i++) {
                        taskList.addAll(teamList.get(i).getTasks());

                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

        allStudentRecyclerView.setAdapter(new TaskAdapter(taskList,this));
        allStudentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        String team1 = sharedPreferences.getString("team", "team");
//        TextView teamName = findViewById(R.id);
//        teamName.setText(team1);



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

    public void intentHelper(View view){
        Intent intent =new Intent(AllTasks.this,TaskDetailPage.class);

        Button b = (Button)view;
        String buttonText = b.getText().toString();
        System.out.println(buttonText);
        intent.putExtra("title",buttonText);
        startActivity(intent);
    }
    public void ctf(View view) {
     intentHelper(view);

    }

    public void code(View view) {
        intentHelper(view);


    }

    public void sleep(View view) {
        intentHelper(view);


    }
}