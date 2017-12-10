package com.contactlistapplication.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.contactlistapplication.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int SPLASH_DISPLAY_LENGTH = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startSplash();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void startSplash() {
        Intent home_intent = new Intent(SplashActivity.this, ContactListingActivity.class);
        startActivity(home_intent);
        overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
        finish();
    }
}
