package com.creater.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class shopping_fire_data_set extends AppCompatActivity {
EditText productName,productDistance,productDesc;
Button submit;
AlertDialog.Builder builder;
AlertDialog dialog;
FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_fire_data_set);
        productName=findViewById(R.id.nameOfProduct);
        productDistance=findViewById(R.id.distanceFromCounter);
        productDesc=findViewById(R.id.descriptionOfProduct);
        submit=findViewById(R.id.submitS);
        submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (!(productDesc.getText().toString() =="") && !(productName.getText().toString() =="")&& !(productDesc.getText().toString() ==""))
               {
                   fireStoreData();
                   productName.setText("");
                   productDistance.setText("");
                   productDesc.setText("");
               }
               else
               {
                   Toast.makeText(getApplicationContext(),"fill all the column",Toast.LENGTH_SHORT).show();
               }
           }
       });
    }
    public void fireStoreData()
    {
        builder=new AlertDialog.Builder(shopping_fire_data_set.this);
        builder.setIcon(R.drawable.error).setMessage("Check Your Internet Connection").
                setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    }
                });
        builder.setCancelable(true);
        Map<String, String> value=new HashMap<>();
        value.put("ProductName",productName.getText().toString());
        value.put("productDistance",productDistance.getText().toString());
        value.put("productDesc",productDesc.getText().toString());
    db.collection("Shopping list").add(value).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
        @Override
        public void onSuccess(DocumentReference documentReference) {
            Toast.makeText(getApplicationContext(),"Data is Saved",Toast.LENGTH_SHORT).show();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            dialog=builder.create();
            dialog.show();
        }
    });
    }
}
