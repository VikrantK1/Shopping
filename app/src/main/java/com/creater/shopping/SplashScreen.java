package com.creater.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class SplashScreen extends AppCompatActivity {
Button manage;
    Handler handler=new Handler();
    Runnable r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);
         manage=findViewById(R.id.manage);
         manage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(SplashScreen.this,shopping_fire_data_set.class));
             }
         });
    }

    @Override
    protected void onResume() {
        super.onResume();
         r=new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,Dashboard.class));
            }
        };

        handler.postDelayed(r,3000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(r);
    }
}
