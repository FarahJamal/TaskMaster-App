package com.example.asac_test.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.asac_test.R;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button signUp = findViewById(R.id.signup);
        EditText username = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(username.getText().toString(), email.getText().toString(), password.getText().toString());

            }
        });
    }
    void signUp(String username, String email, String password) {
        Amplify.Auth.signUp(
                username,
                password,
                AuthSignUpOptions.builder()
                        .userAttribute(AuthUserAttributeKey.email(), email)
                        .build(),
                success -> {
                    Log.i("signUp", "signUp successful: " + success.toString());
                    Intent goToVerification = new Intent(SignUp.this, Verfication.class);
                    goToVerification.putExtra("username", username);
                    goToVerification.putExtra("password", password);
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor2 = pref.edit();
                    editor2.putString("username", username.split(".")[0]); // Storing string
                    editor2.putString("email", email); // Storing string
                    startActivity(goToVerification);
                },
                error -> {
                    Log.e("signUp", "signUp failed: " + error.toString());
                });
    }

    public void signin(View view) {
        Intent goToVerification = new Intent(SignUp.this, SignIn.class);
        startActivity(goToVerification);

    }

    public void ver(View view) {
        Intent goToVerification = new Intent(SignUp.this, Verfication.class);
        startActivity(goToVerification);
    }
}