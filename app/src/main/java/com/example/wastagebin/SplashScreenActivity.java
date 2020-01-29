package com.example.wastagebin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

public class SplashScreenActivity extends AppCompatActivity {
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);


      //  progressBar=(ProgressBar)findViewById(R.id.progressBar);
    //    progressBar.setIndeterminate(true);


        Thread thread = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(4000);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                finally
                {
                    Intent goNext=new Intent(SplashScreenActivity.this,HomeActivity.class);
                    startActivity(goNext);
                    finish();
                }
            }



        };

        thread.start();
    }

    }

