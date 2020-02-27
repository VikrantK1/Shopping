package com.creater.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class shopping_fire_data_set extends AppCompatActivity {
EditText productName,productDistance,productDesc;
Button submit;
FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_fire_data_set);
        productName=findViewById(R.id.nameOfProduct);
        productDistance=findViewById(R.id.distanceFromCounter);
        productDesc=findViewById(R.id.descriptionOfProduct);
        submit=findViewById(R.id.submitS);

    }
    public void fireStoreData()
    {

    }
}
