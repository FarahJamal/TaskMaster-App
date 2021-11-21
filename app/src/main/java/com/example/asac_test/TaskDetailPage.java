package com.example.asac_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.options.StorageDownloadFileOptions;

import java.io.File;

public class TaskDetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_page);
        TextView title=(TextView)findViewById(R.id.title);
        TextView bodies=(TextView)findViewById(R.id.text);
        TextView st=(TextView)findViewById(R.id.status);
TextView teamName=(TextView)findViewById(R.id.teamName);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String t = extras.getString("title");
                String body=extras.getString("body");
                String status=extras.getString("status");
            String team=extras.getString("team");



            if(bodies!=null){

    if(status.equalsIgnoreCase("completed"))
    st.setTextColor(Color.GREEN);
else if(status.equalsIgnoreCase("in progress"))
    st.setTextColor(Color.YELLOW);
else if(status.equalsIgnoreCase("assigned"))
    st.setTextColor(Color.RED);
else
    st.setTextColor(Color.CYAN);
    bodies.setText(body);
    st.setText(status);
     title.setText(t);
     teamName.setText(team);
//     imageView.setImageBitmap();
 }







        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreference = getSharedPreferences("pref", 0);
        String name = sharedPreference.getString("file","");
        System.out.println("file ==> "+name);
        ImageView myimg=findViewById(R.id.imageView2);
        File fileToDownload;
        fileToDownload= new File(getApplicationContext().getFilesDir() +name);

                Amplify.Storage.downloadFile(

                name,
                fileToDownload,
                StorageDownloadFileOptions.defaultInstance(),
                progress -> Log.i("MyAmplifyApp", "Fraction completed: " + progress.getFractionCompleted()),
                result ->
                {     Bitmap bitmap = BitmapFactory.decodeFile(fileToDownload.getPath());
                    System.out.println("this is bitmap ==> "+fileToDownload.getPath());
                    myimg.setImageBitmap(bitmap);
                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName());
                    result.getFile();
                    if (result.getFile() != null){
                        myimg.setImageBitmap(BitmapFactory.decodeFile(result.getFile().getPath()));
                        result.getFile();
                    }
                    },
                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
        );
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent i=new Intent(TaskDetailPage.this,AllTasks.class);
                startActivity(i);
                Toast.makeText(this,"Back button pressed!",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


        }