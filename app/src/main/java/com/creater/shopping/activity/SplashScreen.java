package com.creater.shopping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
Button manage;
    FirebaseAuth authLogin=FirebaseAuth.getInstance();
    Handler handler=new Handler();
    Runnable r;
    FirebaseFirestore store=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash_screen);
   //     getSupportFragmentManager().beginTransaction().add(R.id.splash,new SplashScreen2()).commit();
//         manage=findViewById(R.id.manage);
//         manage.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 startActivity(new Intent(SplashScreen.this,shopping_fire_data_set.class));
//             }
//         });
    }

    @Override
    protected void onResume() {
        super.onResume();
         r=new Runnable() {
            @Override
            public void run() {
                Login login=new Login();
                if (authLogin.getCurrentUser()!=null)
                {
                    store.collection("User").document(authLogin.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot data=task.getResult();
                            if (data.getString("Admin").equals("yes"))
                            {
                             startActivity(new Intent(SplashScreen.this,DashBoardAdmin.class));
                            }
                            else if (data.getString("Admin").equals("no"))
                            {
                                startActivity(new Intent(SplashScreen.this,Dashboard.class));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.splash,login).commit();
                }

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
