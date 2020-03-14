package com.creater.shopping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.creater.shopping.R;
import com.creater.shopping.fragment.Login;
import com.creater.shopping.fragment.SplashScreen2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth authLogin = FirebaseAuth.getInstance();
    Handler handler = new Handler();
    Runnable r;
    FirebaseFirestore store = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

    }

    @Override
    protected void onResume() {
        super.onResume();
        r = new Runnable() {
            @Override
            public void run() {
                Login login = new Login();
                if (authLogin.getCurrentUser() != null) {
                    store.collection("User").document(authLogin.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot data = task.getResult();
                            if (data.getString("Admin").equals("yes")) {
                                startActivity(new Intent(SplashScreen.this, DashBoardAdmin.class));
                                overridePendingTransition(R.anim.zoomin,R.anim.zoomout);
                            } else if (data.getString("Admin").equals("no")) {
                                startActivity(new Intent(SplashScreen.this, Dashboard.class));
                                overridePendingTransition(R.anim.zoomin,R.anim.zoomout);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).replace(R.id.splash, login).commit();
                }

            }
        };

        handler.postDelayed(r, 3000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(r);
    }


}
