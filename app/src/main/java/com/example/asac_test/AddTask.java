package com.example.asac_test;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Status;
import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.storage.StorageAccessLevel;
import com.amplifyframework.storage.options.StorageUploadFileOptions;
import com.example.asac_test.DataBase.AppDatabase;
import com.example.asac_test.Entity.TaskEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    String name="";

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

        // initiate a Switch
        Switch simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);
simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            Button btn=findViewById(R.id.up_f);
            btn.setVisibility(View.INVISIBLE);
            ImageView img=findViewById(R.id.IdProf);
            img.setVisibility(View.VISIBLE);
        }
        else{
            Button btn=findViewById(R.id.up_f);
            btn.setVisibility(View.VISIBLE);
            ImageView img=findViewById(R.id.IdProf);
            img.setVisibility(View.INVISIBLE);
        }
    }
});


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
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
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


//Log.v("spinner",spinner.)
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
        dataStore(editText.getText().toString(),editText2.getText().toString(),vote,team,name);
        List<Team> listOfTasks = new ArrayList<>();
Handler handler;
        handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                return false;
            }
        });
        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    System.out.println("hello there");
                    System.out.println(Team.ID.contains("1"));


                    for (Team team : response.getData()) {
                        listOfTasks.add(team);
                    }
                    Collections.sort(listOfTasks, new Comparator<Team>() {
                        @Override
                        public int compare(Team task, Team t1) {
                            return Long.compare(task.getCreatedAt().toDate().getTime(), t1.getCreatedAt().toDate().getTime());
                        }
                    });
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
        Intent intent = new Intent(AddTask.this, AllTasks.class);
        startActivity(intent);
    }
    private void dataStore(String title,String body,Status status,String id,String fileName){
        TaskMaster task = TaskMaster.builder()
                .title(title)
                .status(status)
                .teamId(id)
                .body(body)
                .s3ImageKey(fileName)
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

    public void upload(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");

        startActivityForResult(intent, 9);

    }

    public void upload_file(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");

        startActivityForResult(intent, 12);
    }
File fileToUpload;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("this is data ==> " + data.getDataString());
        Uri uri ;
        String path = new File(data.getData().getPath()).getAbsolutePath();

        if (path != null) {
            uri = data.getData();

            String filename;
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor == null) filename = uri.getPath();
            else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME);
                filename = cursor.getString(idx);
                cursor.close();
            }

            String ImgName = filename.substring(0, filename.lastIndexOf("."));
            String extension = filename.substring(filename.lastIndexOf(".") + 1);
            System.out.println("name ==> " + ImgName);
            System.out.println("name ==> " + extension);

            fileToUpload = new File(getApplicationContext().getFilesDir(), ImgName+"."+extension);

            if (requestCode == 9) {


                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    FileUtils.copy(inputStream, new FileOutputStream(fileToUpload));
                    name =ImgName+"."+extension;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (requestCode == 12) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    FileUtils.copy(inputStream, new FileOutputStream(fileToUpload));
                    name =ImgName+"."+extension;


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            Log.v(TAG, "file uploaded ===> " + fileToUpload);
            name =ImgName+"."+extension;
            StorageUploadFileOptions options = StorageUploadFileOptions.builder()
                    .accessLevel(StorageAccessLevel.PUBLIC)
                    .build();
            Amplify.Storage.uploadFile(
                    name,

                    fileToUpload,
                    result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );

            SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("file", ImgName+"."+extension); // Storing string
            editor.commit();

        }
    }


}