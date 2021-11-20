package com.example.asac_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

public class Teams extends AppCompatActivity {
Handler handler;
List<String>allTeams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        ArrayList<Team> studentData = new ArrayList<Team>();
        handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Amplify.DataStore.query(Team.class,
                        todos -> {
                            while (todos.hasNext()) {
                                Team todo = todos.next();

                                Log.i("Tutorial", "==== Teams ====");
                                Log.i("Tutorial", "Name: " + todo.getName());
                                studentData.add(todo);
                            }        // get the Recyler view
                        },
                        error->{
                            Log.i("Tutorial", "no teams found");

                        }
                );
                return false;
            }
        });
        System.out.println(studentData+" this students data! ===>");

        RecyclerView allStudentRecyclerView = findViewById(R.id.recyclerview);

        // set a layout manager
        allStudentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // set the adapter for this recycler view
        allStudentRecyclerView.setAdapter(new TeamAdapter(studentData));
getTeams();
//        Button addStudentNavigator = findViewById(R.id.addStudentNavigator);
//        addStudentNavigator.setOnClickListener((v)-> {
//            Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
//            startActivity(intent);
//        });
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

                    System.out.println("all teams list"+allTeams);

                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );


    }

}