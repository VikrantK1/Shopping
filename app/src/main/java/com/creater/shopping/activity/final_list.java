package com.creater.shopping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.creater.shopping.R;
import com.creater.shopping.util.Helper;
import com.creater.shopping.util.RecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class final_list extends AppCompatActivity {
ProgressBar progressBar;
RecyclerView list;
ArrayList<String> shopinglist;
ArrayList<Helper> optimum_List=new ArrayList<>();
ArrayList<String> templist=new ArrayList<>();
RecyclerAdapter adapter;
int count=1;
Toolbar toolbar;
AlertDialog.Builder builder;
AlertDialog dialog;
EditText text;
FirebaseAuth auth=FirebaseAuth.getInstance();
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
        builder=new AlertDialog.Builder(this);
      new  optimumListGenerater().execute();
    }
    class optimumListGenerater extends AsyncTask<Void,Integer,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           progressBar.setMax(shopinglist.size());
           progressBar.setVisibility(View.VISIBLE);
           progressBar.setProgress(0);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            Log.i("count", String.valueOf(values[0]));
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
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        finish();
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
            View v=getLayoutInflater().inflate(R.layout.savediloge,null,false);
            CardView button=v.findViewById(R.id.savedD);
            text=v.findViewById(R.id.diloge);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String  data=text.getText().toString();
                    if (TextUtils.isEmpty(data))
                    {
                        text.setError("Enter the first");
                    }
                    else
                    {
                        saveData();
                    }

                }
            });
           dialog=builder.create();
           dialog.setView(v);
           dialog.show();
        }
        if (item.getItemId()==R.id.EditMenu)
        {
            Intent intent=new Intent(final_list.this,MainActivity.class);
            for (int i = 0; i <optimum_List.size(); i++) {
                if (!templist.contains(optimum_List.get(i).getProduct_Name())) {
                    templist.add(optimum_List.get(i).getProduct_Name());
                }
            }
            intent.putStringArrayListExtra("EditList",templist);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void saveData()
    {
        AlertDialog.Builder builder23=new AlertDialog.Builder(final_list.this);
        final AlertDialog dialog34=builder23.create();
        dialog34.setView(getLayoutInflater().inflate(R.layout.progessdiloge,null,false));
        dialog34.show();
        Calendar c=Calendar.getInstance();
        int day=c.get(Calendar.DAY_OF_MONTH);
        int month=c.get(Calendar.MONTH);
        int year=c.get(Calendar.YEAR);
        int hour=c.get(Calendar.HOUR_OF_DAY);
        int min=c.get(Calendar.MINUTE);
        int sec=c.get(Calendar.SECOND);
        month+=1;
        final StringBuilder builder=new StringBuilder();
        builder.append(day+"/").append(month+"/").append(year);

         templist.add(builder.toString().trim());
        for (int i = 0; i <optimum_List.size(); i++) {
            if (!templist.contains(optimum_List.get(i).getProduct_Name())) {
                templist.add(optimum_List.get(i).getProduct_Name());
            }
        }

        Log.i("Date",builder.toString().trim());
        final Map<String,ArrayList<String>> values=new HashMap<>();
        values.put("ListData",templist);
        db23.collection("User").document(auth.getCurrentUser().getUid()).collection("List").document(text.getText().toString().trim()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists())
                {
                    dialog34.dismiss();
                    text.setError("List is already Exist");
                }
                else
                {
                    db23.collection("User").document(auth.getCurrentUser().getUid()).collection("List").
                            document(text.getText().toString().trim()).set(values).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(),"List is Saved",Toast.LENGTH_SHORT).show();
                            dialog34.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog34.dismiss();
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

}
