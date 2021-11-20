package com.example.asac_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.targeting.TargetingClient;
import com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile.EndpointProfile;
import com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile.EndpointProfileUser;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

import com.amplifyframework.datastore.generated.model.Status;
import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.asac_test.lambda.MyInterface;
import com.example.asac_test.lambda.RequestClass;
import com.example.asac_test.lambda.ResponseClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import com.amazonaws.mobileconnectors.lambdainvoker.*;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
public class MyTasks extends AppCompatActivity {
    private static final String TAG ="MyTasks" ;
    ChipNavigationBar chipNavigationBar;
TextView user;
TextView mail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
        String username = sharedPreferences.getString("username",null);
        String email = sharedPreferences.getString("email",null);
        user=(TextView)findViewById(R.id.username);
        mail=(TextView)findViewById(R.id.email);
        user.setText(username);
        mail.setText(email);
        /**AWS stuff**/
        createTeams();

eventRecord1();
        /**end here**/

        chipNavigationBar=findViewById(R.id.chip);
        chipNavigationBar.setOnItemSelectedListener(i -> {
            Log.v("id value => ",i+"");

            switch (i){
                case R.id.item0:
                {
                    Log.v("id => ",i+"");

                    Intent activity1=new Intent(getBaseContext(),MyTasks.class);
                    startActivity(activity1);
                }

                case R.id.item1: {
                    Log.v("id => ",i+"");

                    Intent activity2 = new Intent(getBaseContext(), AddTask.class);
                    startActivity(activity2);
                }

                case R.id.item2: {
                    Log.v("id => ",i+"");

                    Intent activity3 = new Intent(getBaseContext(), SettingsPage.class);
                    startActivity(activity3);
                }

                case R.id.item3: {
                    Log.v("id => ",i+"");

                    Intent activity4 = new Intent(getBaseContext(), AddTask.class);
                    startActivity(activity4);
                }
            }
        });
//        RequestClass request = new RequestClass("farah", "farahsarese@gmail.com");
//MyTask myTask=new MyTask();
//myTask.execute(request);
    }
    private  class MyTask extends AsyncTask<RequestClass, Void, ResponseClass> {

        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-west-2:e41aaf64-7ae0-47ad-9743-e8a67b5d081f", // Identity pool ID

                Regions.US_WEST_2 // Region
        );
        // Create LambdaInvokerFactory, to be used to instantiate the Lambda proxy.
        LambdaInvokerFactory factory = new LambdaInvokerFactory(getApplicationContext(),
                Regions.US_WEST_2, credentialsProvider);

        // Create the Lambda proxy object with a default Json data binder.
// You can provide your own data binder by implementing
// LambdaDataBinder.
        final MyInterface myInterface = factory.build(MyInterface.class);


        // The Lambda function invocation results in a network call.
// Make sure it is not called from the main thread.
        @Override
        protected ResponseClass doInBackground(RequestClass... params) {
            // invoke "echo" method. In case it fails, it will throw a
            // LambdaFunctionException.
            try {
                return myInterface.AndroidBackendLambdaFunction2(params[0]);
            } catch (LambdaFunctionException lfe) {
                Log.e("Tag", "Failed to invoke echo", lfe);
                return null;
            }
        }

        @Override
        protected void onPostExecute(ResponseClass result) {
            if (result == null) {
                return;
            }

            // Do a toast
            Toast.makeText(MyTasks.this, result.getGreetings(), Toast.LENGTH_LONG).show();
        }
    }

    private void createTeams(){

        Team todo1 = Team.builder()
                .name("Team 1").id("1")
                .users(new ArrayList<>())
                .build();

        Amplify.API.mutate(
                ModelMutation.create(todo1),
                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
        Team todo2 = Team.builder()
                .name("Team 2").id("2")
                .users(new ArrayList<>())

                .build();

        Amplify.API.mutate(
                ModelMutation.create(todo2),
                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
        Team todo3 = Team.builder()
                .name("Team 3").id("3")
                .users(new ArrayList<>())

                .build();

        Amplify.API.mutate(
                ModelMutation.create(todo3),
                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
    }

    public void add(View view) {
        Intent intent=new Intent(MyTasks.this, AddTask.class);
        startActivity(intent);
    }

    public void all(View view) {
        Intent intent=new Intent(MyTasks.this,AllTasks.class);
        startActivity(intent);
    }


    public void setting(View view) {
        Intent i=new Intent(MyTasks.this,SettingsPage.class);
        startActivity(i);

    }
    /***AWS**/


    public void go(View view) {
Intent i=new Intent(this,Teams.class);
startActivity(i);
    }

    /********/
    private static PinpointManager pinpointManager;
    public static PinpointManager getPinpointManager(final Context applicationContext) {
        if (pinpointManager == null) {
            final AWSConfiguration awsConfig = new AWSConfiguration(applicationContext);
            AWSMobileClient.getInstance().initialize(applicationContext, awsConfig, new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    Log.i(TAG, userStateDetails.getUserState().toString());
                }

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, "Initialization error.", e);
                }
            });

            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    applicationContext,
                    AWSMobileClient.getInstance(),
                    awsConfig);

            pinpointManager = new PinpointManager(pinpointConfig);

            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }
                            final String token = task.getResult();
                            Log.d(TAG, "Registering push notifications token: " + token);
                            pinpointManager.getNotificationClient().registerDeviceToken(token);
                        }
                    });
        }
        return pinpointManager;
    }
    private void eventRecord1() {
        String userName = getSharedPreferences("MyPref", MODE_PRIVATE).getString("username", "no user info");

        AnalyticsEvent _event = AnalyticsEvent.builder()
                .name("MyTasks")
                .addProperty("UserName", userName)
                .build();

        Amplify.Analytics.recordEvent(_event);
    }
    public void assignUserIdToEndpoint() {
        TargetingClient targetingClient = pinpointManager.getTargetingClient();
        EndpointProfile endpointProfile = targetingClient.currentEndpoint();
        EndpointProfileUser endpointProfileUser = new EndpointProfileUser();
        endpointProfileUser.setUserId("UserIdValue");
        endpointProfile.setUser(endpointProfileUser);
        targetingClient.updateEndpointProfile(endpointProfile);
        Log.d(TAG, "Assigned user ID " + endpointProfileUser.getUserId() +
                " to endpoint " + endpointProfile.getEndpointId());
    }
}