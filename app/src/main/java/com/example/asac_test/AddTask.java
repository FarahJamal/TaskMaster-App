package com.example.asac_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Status;
import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.asac_test.DAO.TaskDAO;
import com.example.asac_test.DataBase.AppDatabase;
import com.example.asac_test.Entity.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {
    private AppDatabase appDataBase;
    TaskDAO taskDao;
    private static final String TAG ="AddTask" ;
    Status vote=null;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    List<Team> teams=new ArrayList<>();
    //    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "com.example.asac_test" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        appDataBase= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"task").allowMainThreadQueries().build();
        taskDao =appDataBase.taskDAO();
        addTeam();

        //To have the back button!!
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent i=new Intent(AddTask.this,MyTasks.class);
                startActivity(i);
                Toast.makeText(this,"Back button pressed!",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    int counter=0;
    private void dataStore(String title,String body,Status status,Team team){
        TaskMaster taskMaster = TaskMaster.builder()
                .title(title)
.status(status)
                .team(team)
                .body(body)
                .build();

        Amplify.API.mutate(
                ModelMutation.create(taskMaster),
                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );


    }
    public void addTeam(){

        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Team.class),
                response -> {
                    for (Team todo : response.getData()) {
                        teams.add(todo);
                        System.out.println(teams);
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
    }
    public void click(View view) {

        EditText editText =(EditText) findViewById(R.id.edit1) ;
        String text=editText.getText().toString();

        EditText editText2 =(EditText) findViewById(R.id.edit2) ;
        String text2=editText2.getText().toString();


        TextView count=(TextView)findViewById(R.id.counter);

        if(text.isEmpty() && text2.isEmpty()){
            Toast message= Toast.makeText(getBaseContext(),"you should fill both fields first!",Toast.LENGTH_LONG);
            message.show();
        }
        else{
            counter++;
            Intent i=new Intent(AddTask.this,AllTasks.class);

            Toast message= Toast.makeText(getBaseContext(),"you have successfully add your task!",Toast.LENGTH_LONG);
            startActivity(i);
            message.show();

        }








        // get the selected RadioButton of the group
        selectedRadioButton  = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
        //get RadioButton text
        String yourVote = selectedRadioButton.getText().toString();
        // display it as Toast to the user
        Toast.makeText(AddTask.this, "Selected Radio Button is:" + yourVote , Toast.LENGTH_LONG).show();
        Log.v("selected radio ==>",yourVote);

        if(yourVote=="new"){
            vote=Status.NEW;
        }
        else if(yourVote=="completed"){
            vote=Status.COMPLETED;
        }
        else if(yourVote =="in_progress"){
            vote=Status.IN_PROGRESS;
        }
        else{
            vote=Status.NEWVALUE;
        }
        dataStore(text,text2,Status.IN_PROGRESS,teams.get(0));

        //Save a TaskModel
        TaskEntity taskModel = new TaskEntity(text, text2, yourVote);
        AppDatabase.getInstance(getApplicationContext()).taskDAO().insert(taskModel);
        Intent intent = new Intent(AddTask.this, AllTasks.class);
        startActivity(intent);
    }

}