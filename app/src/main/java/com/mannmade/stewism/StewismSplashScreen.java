package com.mannmade.stewism;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;


public class StewismSplashScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stewism_splash_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        new CountDownTimer(2500, 1) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Intent startApp = new Intent(StewismSplashScreen.this, StewismMainActivity.class);
                startActivity(startApp);
                finish();
            }
        }.start();

    }


}
