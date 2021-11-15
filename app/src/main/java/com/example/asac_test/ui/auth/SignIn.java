package com.example.asac_test.ui.auth;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.example.asac_test.MyTasks;
import com.example.asac_test.R;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        configureAmplify();
        Button signIn=findViewById(R.id.signIn);
        EditText username=findViewById(R.id.username2);
        EditText password=findViewById(R.id.password2);
        Button signUp=findViewById(R.id.signUp2);

        signIn.setOnClickListener(view -> signInfunc(username.getText().toString(),password.getText().toString()));

        signUp.setOnClickListener(view ->
        { Intent intent=new Intent(SignIn.this,SignUp.class);
            startActivity(intent);
        });

    }
    void signInfunc(String userName, String password){
        Amplify.Auth.signIn(
                userName,
                password,
                success ->{
                    Log.i("signIn", "signIn successful: " + success.toString());
                    Intent goToMain = new Intent(SignIn.this, MyTasks.class);


                    startActivity(goToMain);
                },
                error ->{
                    Log.e("signIn", "signIn failed: " + error.toString());
                }
        );

    }

    private void configureAmplify() {
        try {

            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());

            Amplify.configure(getApplicationContext());
            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException failure) {
            Log.e("Tutorial", "Could not initialize Amplify", failure);
        }
        Amplify.DataStore.observe(TaskMaster.class,
                started -> Log.i("Tutorial", "Observation began."),
                change -> Log.i("Tutorial", change.item().toString()),
                failure -> Log.e("Tutorial", "Observation failed.", failure),
                () -> Log.i("Tutorial", "Observation complete.")
        );
    }

}