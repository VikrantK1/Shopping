package com.creater.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.creater.shopping.util.Helper;
import com.creater.shopping.util.RecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class final_list extends AppCompatActivity {
ProgressBar progressBar;
RecyclerView list;
ArrayList<String> shopinglist;
ArrayList<Helper> optimum_List=new ArrayList<>();
ArrayList<Helper> templist=new ArrayList<>();
RecyclerAdapter adapter;
int count=1;
Toolbar toolbar;
FirebaseFirestore db23=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_list);
        progressBar=findViewById(R.id.progess);
        list=findViewById(R.id.recyclerView);
        toolbar=findViewById(R.id.toolbar);
        getSupportActionBar().hide();
        setActionBar(toolbar);
        Bundle bundle=getIntent().getExtras();
        shopinglist=bundle.getStringArrayList("ShoppingList");
        adapter=new RecyclerAdapter(this,optimum_List);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
      new  optimumListGenerater().execute();
    }
    class optimumListGenerater extends AsyncTask<Void,Integer,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           progressBar.setMax(shopinglist.size());

           progressBar.setProgress(0);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);


        }

        @Override
        protected String doInBackground(Void... voids) {

            for (int i = 0; i <shopinglist.size(); i++) {
                db23.collection("Shopping list").whereEqualTo("ProductName",shopinglist.get(i)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc:task.getResult())
                        {
                            Helper data=new Helper(doc.getString("ProductName"),doc.getString("productDistance"),doc.getString("productDesc"));
                            optimum_List.add(data);

                            optimum_List=BestPath(optimum_List);
                            adapter.notifyDataSetChanged();
                            publishProgress(count);
                            count++;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }

            return "Complete";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
             progressBar.setVisibility(View.INVISIBLE);
        }
    }
    public static   ArrayList<Helper>  BestPath(ArrayList<Helper> list)
    {
        for (int i = 0; i <list.size() ; i++) {
            for (int j = i; j <list.size() ; j++) {
                double data=Double.parseDouble(list.get(i).getProduct_Distance());
                double dataJ=Double.parseDouble(list.get(j).getProduct_Distance());
                if (data < dataJ)
                {
                    Helper temp=list.get(i);
                    list.set(i,list.get(j));
                    list.set(j,temp);
                }
            }
        }
        return list;
    }

    @Override
    protected void onStop() {
        super.onStop();
        optimum_List.removeAll(optimum_List);
        Log.i("optmum",optimum_List.size()+"");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.saveFinal)
        {
            Toast.makeText(getApplicationContext(),"I am selected body",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
