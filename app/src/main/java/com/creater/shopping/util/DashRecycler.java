package com.creater.shopping.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.creater.shopping.activity.CreateList;
import com.creater.shopping.activity.MainActivity;
import com.creater.shopping.R;
import com.creater.shopping.activity.savedList;
import com.creater.shopping.activity.shopping_fire_data_set;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DashRecycler extends RecyclerView.Adapter<DashRecycler.Holder> {
Context mcontext;
ArrayList<DashContener> data;
public DashRecycler(Context context, ArrayList<DashContener> list23)
{
    this.mcontext=context;
    this.data=list23;
}
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.carddata,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
     holder.imageView.setImageResource(data.get(position).getImg());
        holder.textView.setText(data.get(position).getValue());
        if (position==2)
        {

            firebasehelp.store.collection("User").document(firebasehelp.auth.getCurrentUser().getUid())
                    .collection("List").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    holder.count.setVisibility(View.VISIBLE);
                    QuerySnapshot doc=task.getResult();
                    holder.count.setText(""+doc.size());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(mcontext,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==0)
                {
                    mcontext.startActivity(new Intent(mcontext, CreateList.class));
                }
                if (position==1)
                {
                    mcontext.startActivity(new Intent(mcontext, MainActivity.class));
                }
                if (position==2)
                {

                 mcontext.startActivity(new Intent(mcontext, savedList.class));
                }
                if (position==3)
                {
                    mcontext.startActivity(new Intent(mcontext, shopping_fire_data_set.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
    CardView cardView;
    TextView textView;
    ImageView imageView;
    TextView  count;
        public Holder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.dashcard);
            textView=itemView.findViewById(R.id.dashText);
            imageView=itemView.findViewById(R.id.dashImage);
            count=itemView.findViewById(R.id.count);
        }
    }
}
