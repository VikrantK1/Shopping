package com.creater.shopping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.creater.shopping.R;
import com.creater.shopping.util.CreateRecycler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CreateList extends AppCompatActivity {
ArrayList<String > list=new ArrayList<>();
CreateRecycler recycler;
RecyclerView recyclerView;
FloatingActionButton floatingActionButton;
ProgressBar progressBar;
Toolbar toolbar;
FirebaseFirestore db34=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        recyclerView=findViewById(R.id.recyclerCreate);
        toolbar=findViewById(R.id.toolbar);
        getSupportActionBar().hide();
        setActionBar(toolbar);
        floatingActionButton=findViewById(R.id.floatingButton);
        progressBar=findViewById(R.id.progessFull);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recycler.created.size()!=0)
                {
                    Intent intent=new Intent(CreateList.this,final_list.class);
                    intent.putStringArrayListExtra("ShoppingList",recycler.created);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Select Something",Toast.LENGTH_SHORT).show();
                }

            }
        });
        recycler=new CreateRecycler(this,list);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(4,10,4,10);
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycler);

        new backgroundTask().execute();
    }
    class  backgroundTask extends AsyncTask<Void,Integer,String>
    {




        @Override
        protected String doInBackground(Void... voids) {
            db34.collection("Shopping list").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot doc:task.getResult()
                    ) {
                        list.add(doc.getString("ProductName"));
                        recycler.notifyDataSetChanged();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Check Internet Connection",Toast.LENGTH_SHORT).show();
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Complete";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
       recycler.created.removeAll(recycler.created);
    }
}
