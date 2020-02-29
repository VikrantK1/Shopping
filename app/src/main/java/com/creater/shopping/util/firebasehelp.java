package com.creater.shopping.util;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.creater.shopping.activity.DashBoardAdmin;
import com.creater.shopping.activity.Dashboard;
import com.creater.shopping.activity.SplashScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class firebasehelp {
   public static FirebaseAuth auth=FirebaseAuth.getInstance();
    public static FirebaseFirestore store=FirebaseFirestore.getInstance();
     static String data23;
    public static String checkDashboard()
    {

        store.collection("User").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot data=task.getResult();
                if (data.getString("Admin").equals("yes"))
                {
                   data23="yes" ;
                }
                else if (data.getString("Admin").equals("no"))
                {
                   data23="no";
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        return data23;
    }


}
