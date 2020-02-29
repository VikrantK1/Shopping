package com.creater.shopping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

import com.creater.shopping.R;
import com.creater.shopping.util.firebasehelp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class savedList extends AppCompatActivity {
FirebaseFirestore store=FirebaseFirestore.getInstance();
FirebaseAuth auth=FirebaseAuth.getInstance();
Toolbar toolbar;
public Context context=savedList.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list);
        getSupportActionBar().hide();
        toolbar=findViewById(R.id.toolbar);


        setActionBar(toolbar);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onBackPressed() {
        if (firebasehelp.checkDashboard()=="yes")
        {
            startActivity(new Intent(savedList.this,DashBoardAdmin.class));
        }
        else if (firebasehelp.checkDashboard()=="no")
        {
            startActivity(new Intent(savedList.this,Dashboard.class));
        }
    }
}
