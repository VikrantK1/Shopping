package com.creater.shopping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.creater.shopping.R;
import com.creater.shopping.util.ListCreating;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView text;
    Button submit;
    RecyclerView shopinglist;
    String[] list = {"vikr", "vata", "meta", "mata", "sata"};
    ArrayList<String> suggestionList = new ArrayList<>();
    ListCreating arrAdapter;
    ImageView addbtn;
    ArrayList<String> shopping = new ArrayList<>();
    FirebaseFirestore db34 = FirebaseFirestore.getInstance();
    ArrayAdapter<String> ad;
    Toolbar toolbar;
    backgroundTask task = new backgroundTask();
    ArrayList<String> editlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initilation();
        editTextFun();
        addToList();

        SubmitList();
        getSupportActionBar().hide();
        setActionBar(toolbar);
        task.execute();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            editlist = bundle.getStringArrayList("EditList");
            shopping.addAll(editlist);
            arrAdapter.notifyDataSetChanged();


        }
    }

    public void initilation() {
        text = findViewById(R.id.addList);
        toolbar = findViewById(R.id.toolbar);
        submit = findViewById(R.id.submitM);
        shopinglist = findViewById(R.id.shoppingList);
        addbtn = findViewById(R.id.addbtn);
        arrAdapter = new ListCreating(MainActivity.this, shopping);
        shopinglist.setLayoutManager(new LinearLayoutManager(this));
        shopinglist.setAdapter(arrAdapter);

    }

    public void editTextFun() {
        ad = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, suggestionList);
        text.setAdapter(ad);
        text.setThreshold(1);
    }

    class backgroundTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... voids) {
            db34.collection("Shopping list").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot doc : task.getResult()
                    ) {
                        suggestionList.add(doc.getString("ProductName"));
                        ad.notifyDataSetChanged();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            });
            return "Complete";
        }


    }

    public void addToList() {
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(text.getText().toString())) {
                    if (!shopping.contains(text.getText().toString())) {
                        shopping.add(text.getText().toString().trim());
                        arrAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "Added before in list", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    text.setError("Enter name First");
                }
                text.setText("");
            }

        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void SubmitList() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopping.size() != 0) {
                    Intent intent = new Intent(MainActivity.this, final_list.class);
                    intent.putStringArrayListExtra("ShoppingList", shopping);
                    finish();
                    startActivity(intent);
                }

            }
        });
    }
}
