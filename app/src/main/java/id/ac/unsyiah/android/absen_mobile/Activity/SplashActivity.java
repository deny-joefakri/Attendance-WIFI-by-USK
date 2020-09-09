package id.ac.unsyiah.android.absen_mobile.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import id.ac.unsyiah.android.absen_mobile.SharedPreferences.DataStore;
import id.ac.unsyiah.android.absen_mobile.R;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DataStore ts = new DataStore(SplashActivity.this);
                Log.e("token", ts.getToken());
                if (ts.hasToken()) {
                    Intent intent = new Intent(SplashActivity.this, MainDrawerActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
                else{
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}