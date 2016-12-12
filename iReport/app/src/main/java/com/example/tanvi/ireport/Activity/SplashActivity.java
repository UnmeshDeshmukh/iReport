package com.example.tanvi.ireport.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tanvi.ireport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final long DELAY = 3000;
    private boolean scheduled1 = false;
    private boolean scheduled2 =false;
    private Timer splashTimer1, splashTimer2;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        /**************************************************************************************************
         Setting the timer for the splash screen
         **************************************************************************************************/
        if (user!=null) {
            Log.i("","User is Present !!!!!!!!!!!!!!!!!" +FirebaseAuth.getInstance().getCurrentUser().getEmail());
            splashTimer1 = new Timer();
            splashTimer1.schedule(new TimerTask() {
                @Override
                public void run() {
                    SplashActivity.this.finish();

                }
            }, DELAY);

            scheduled1 = true;

        }

        else
        {
            splashTimer2 = new Timer();
            splashTimer2.schedule(new TimerTask() {
                @Override
                public void run() {
                    SplashActivity.this.finish();

                }
            }, DELAY);
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            scheduled2 = true;



        }

    }
    /**************************************************************************************************
     Destroying Splash Activity
     **************************************************************************************************/

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (scheduled1) {
            splashTimer1.cancel();
            splashTimer1.purge();
        }
        if(scheduled2) {
            splashTimer2.cancel();
            splashTimer2.purge();
        }
    }
}
