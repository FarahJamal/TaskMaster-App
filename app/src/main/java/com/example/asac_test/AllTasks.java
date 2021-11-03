package com.example.asac_test;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asac_test.DataBase.AppDatabase;
import com.example.asac_test.Entity.TaskEntity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
        List<TaskEntity> taskData = AppDatabase.getInstance(getApplicationContext()).taskDAO().getAll();

        RecyclerView allStudentRecyclerView = findViewById(R.id.recTask);

        allStudentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        allStudentRecyclerView.setAdapter(new TaskAdapter((ArrayList<TaskEntity>) taskData));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {

        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String title = prefs.getString("title","title");
        String desc = prefs.getString("description","description");
        String counter = prefs.getString("counter","total:0");
        int number=prefs.getInt("number",0);
//        for (int i=0;i<2;i++){
//            ConstraintLayout ll = (ConstraintLayout)findViewById(R.id.all);
//
//            Button btn = new Button(this);
//
//            btn.setText("Task "+i);
//            btn.setId(i);
//            btn.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
//            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) btn.getLayoutParams();
//            layoutParams.addRule(RelativeLayout.BELOW, button.getId());
//            layoutParams.addRule(RelativeLayout.ALIGN_LEFT, button.getId());
//            textView.setLayoutParams(layoutParams);
//
//            ll.addView(btn);
//
//        }
        TextView textView=(TextView)findViewById(R.id.text1);
        TextView counterText=(TextView)findViewById(R.id.counter);
        textView.setText(title +"\n"+desc);
        counterText.setText(counter);


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