package com.example.asac_test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.chyrta.onboarder.OnboarderActivity;
import com.chyrta.onboarder.OnboarderPage;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends OnboarderActivity {

    List<OnboarderPage> onboarderPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onboarderPages = new ArrayList<OnboarderPage>();

        // Create your first page
        OnboarderPage onboarderPage1 = new OnboarderPage("Manage Your Tasks", "Organize all your To-Do's lists. tag them and manage your time!",R.drawable.p1);
        OnboarderPage onboarderPage2 = new OnboarderPage("Saving Time and Money", "Time is money: use this advice to get the most from every job you do.",R.drawable.p2);

        // You can define title and description colors (by default white)
        onboarderPage1.setTitleColor(R.color.white);
        onboarderPage1.setDescriptionColor(R.color.white);

        // Don't forget to set background color for your page
        onboarderPage1.setBackgroundColor(R.color.purple);
        // You can define title and description colors (by default white)
        onboarderPage2.setTitleColor(R.color.white);
        onboarderPage2.setDescriptionColor(R.color.white);
        setDividerColor(Color.BLACK);
        // Don't forget to set background color for your page
        onboarderPage2.setBackgroundColor(R.color.purple);
        setActiveIndicatorColor(android.R.color.white);
        setInactiveIndicatorColor(android.R.color.darker_gray);
        shouldDarkenButtonsLayout(true);
        // Add your pages to the list
        onboarderPages.add(onboarderPage1);
        onboarderPages.add(onboarderPage2);
        shouldUseFloatingActionButton(true);

        // And pass your pages to 'setOnboardPagesReady' method
        setOnboardPagesReady(onboarderPages);

    }

    @Override
    public void onSkipButtonPressed() {
        // Optional: by default it skips onboarder to the end
        super.onSkipButtonPressed();
        Intent i =new Intent(IntroActivity.this, MyTasks.class);
        startActivity(i);
        // Define your actions when the user press 'Skip' button
    }

    @Override
    public void onFinishButtonPressed() {
        Intent i =new Intent(IntroActivity.this, MyTasks.class);
        startActivity(i);    }

}