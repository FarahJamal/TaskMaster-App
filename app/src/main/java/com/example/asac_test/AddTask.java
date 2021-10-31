package com.example.asac_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        //To have the back button!!
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
            Toast message= Toast.makeText(getBaseContext(),"you have successfully add your task!",Toast.LENGTH_LONG);
            count.setText("total:"+counter);

            startActivity(i);
            message.show();
        }

}
}