package com.example.asac_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Status;
import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.asac_test.DataBase.AppDatabase;
import com.example.asac_test.Entity.TaskEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddTask extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG ="AddTask" ;
    List<Team> teamList=new ArrayList<>();
    String team="";
    Status vote=null;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    Spinner  spinner ;
    ArrayList <String>allTeams=new ArrayList<>();
    //    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "com.example.asac_test" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //To have the back button!!
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        radioGroup = (RadioGroup) findViewById(R.id.radio);





    getTeams();

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
    private void getTeams() {
        Amplify.DataStore.query(Team.class,
                todos -> {
                    while (todos.hasNext()) {
                        Team todo = todos.next();

                        Log.i("Tutorial", "==== Teams ====");
                        Log.i("Tutorial", "Name: " + todo.getName());
                        allTeams.add(todo.getName());
                    }
                    spinner= (Spinner)findViewById(R.id.planets_spinner);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, allTeams);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(arrayAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String tutorialsName = parent.getItemAtPosition(position).toString();
                            Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,Toast.LENGTH_LONG).show();
                            team=tutorialsName;
                        }
                        @Override
                        public void onNothingSelected(AdapterView <?> parent) {
                        }
                    });
                    System.out.println("all teams list"+allTeams);

                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );


    }

    int counter=0;

    public void click(View view) {

        EditText editText =(EditText) findViewById(R.id.edit1) ;
        String text=editText.getText().toString();

        EditText editText2 =(EditText) findViewById(R.id.edit2) ;
        String text2=editText2.getText().toString();


        TextView count=(TextView)findViewById(R.id.counter);
//Log.v("spinner",spinner.)
        if(text.isEmpty() && text2.isEmpty()){
            Toast message= Toast.makeText(getBaseContext(),"you should fill both fields first!",Toast.LENGTH_LONG);
            message.show();
        }
        else{
            counter++;
            Intent i=new Intent(AddTask.this,AllTasks.class);

            Toast message= Toast.makeText(getBaseContext(),"you have successfully add your task!",Toast.LENGTH_LONG);
            count.setText("total:"+counter);
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

        if(yourVote.equalsIgnoreCase("completed"))
            vote=Status.COMPLETED;
        else if(yourVote.equalsIgnoreCase("in progress"))
            vote=Status.IN_PROGRESS;
        else if(yourVote.equalsIgnoreCase("assigned"))
            vote=Status.NEWVALUE;
        else
            vote=Status.NEW;


        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    if(response.getData().getRequestForNextResult()==null){
                        System.out.println(response.getData().getRequestForNextResult());

                        Log.i("Teams", "Successful query, found teams."+response.getData());
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
Log.v("voted ==>",vote.toString());
        dataStore(editText.getText().toString(),editText2.getText().toString(),vote,team);


        Intent intent = new Intent(AddTask.this, AllTasks.class);
        startActivity(intent);
    }
    private void dataStore(String title,String body,Status status,String id){
        TaskMaster task = TaskMaster.builder()
                .title(title)
                .status(status)
                .teamId(id)
                .body(body)
                .build();


        Amplify.API.mutate(ModelMutation.create(task), succuess-> {
            Log.i(TAG, "Saved to DYNAMODB");
        }, error -> {
            Log.i(TAG, "error saving to DYNAMODB");
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Log.v("spinner",text);
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}