package com.creater.shopping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.creater.shopping.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class shopping_fire_data_set extends AppCompatActivity {
    EditText productName, productDistance, productDesc,productfloor;
    Button submit;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_fire_data_set);
        productName = findViewById(R.id.nameOfProduct);
        getSupportActionBar().hide();
        productDistance = findViewById(R.id.distanceFromCounter);
        productDesc = findViewById(R.id.descriptionOfProduct);
        submit = findViewById(R.id.submitS);
        toolbar = findViewById(R.id.toolbar);
        productfloor=findViewById(R.id.floor);
        setActionBar(toolbar);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(productDesc.getText()) ||
                        (TextUtils.isEmpty(productName.getText())) ||
                        (TextUtils.isEmpty(productDistance.getText()))||
                        (TextUtils.isEmpty(productfloor.getText()))) {
                    Toast.makeText(getApplicationContext(), "fill all the column", Toast.LENGTH_SHORT).show();

                } else {
                    fireStoreData();
                    productName.setText("");
                    productDistance.setText("");
                    productDesc.setText("");
                    productfloor.setText("");

                }
            }
        });
    }

    public void fireStoreData() {
        AlertDialog.Builder builderp = new AlertDialog.Builder(shopping_fire_data_set.this);
        final AlertDialog dialogp = builderp.create();
        dialogp.setView(getLayoutInflater().inflate(R.layout.progessdiloge, null, false));
        dialogp.show();
        builder = new AlertDialog.Builder(shopping_fire_data_set.this);
        builder.setIcon(R.drawable.error).setMessage("Check Your Internet Connection").
                setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setCancelable(true);
        Map<String, String> value = new HashMap<>();
        value.put("ProductName", productName.getText().toString().trim());
        value.put("productDistance", productDistance.getText().toString().trim());
        value.put("productDesc", productDesc.getText().toString().trim());
        value.put("productFloor",productfloor.getText().toString().trim());
        db.collection("Shopping list").add(value).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                dialogp.dismiss();
                Toast.makeText(getApplicationContext(), "Data is Saved", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialogp.dismiss();
                dialog = builder.create();
                dialog.show();
            }
        });
    }
}
