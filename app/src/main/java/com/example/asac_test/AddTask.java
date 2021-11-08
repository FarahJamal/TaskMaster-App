package com.example.asac_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

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

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Status;
import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.example.asac_test.DataBase.AppDatabase;
import com.example.asac_test.Entity.TaskEntity;

public class AddTask extends AppCompatActivity {
    private static final String TAG ="AddTask" ;
Status vote=null;
    RadioGroup radioGroup;
        RadioButton selectedRadioButton; 
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
    private void dataStore(String title,String body,Status status){
        TaskMaster task = TaskMaster.builder()
                .title(title)
                .status(status)
                .body(body)
                .build();

        // save with the datastore
        Amplify.DataStore.save(task, result -> {
            Log.i(TAG, "Task Saved");
        }, error -> {
            Log.i(TAG, "Task Not Saved");
        });

        // query with the datastore
        Amplify.DataStore.query(
                TaskMaster.class,
                queryMatches -> {
                    while (queryMatches.hasNext()) {
                        Log.i(TAG, "Successful query, found tasks.");
                        TaskMaster taskMaster = queryMatches.next();
                        Log.i(TAG, taskMaster.getTitle());
//                        label.setText(taskMaster.getTitle());
                    }
                },
                error -> {
                    Log.i(TAG,  "Error retrieving tasks", error);
                });
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
            i.putExtra("edit1",text);
            i.putExtra("edit2",text2);
            i.putExtra("counter",counter);
            Toast message= Toast.makeText(getBaseContext(),"you have successfully add your task!",Toast.LENGTH_LONG);
            count.setText("total:"+counter);
            startActivity(i);
            message.show();

//            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("title",text );
            editor.putString("description",text2 );
            editor.putString("counter","total:"+counter );
            editor.apply();
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
        dataStore(editText.getText().toString(),editText2.getText().toString(),vote);

        //Save a TaskModel
        TaskEntity taskModel = new TaskEntity(text, text2, yourVote);
        AppDatabase.getInstance(getApplicationContext()).taskDAO().insert(taskModel);
        Intent intent = new Intent(AddTask.this, AllTasks.class);
        startActivity(intent);
}

}