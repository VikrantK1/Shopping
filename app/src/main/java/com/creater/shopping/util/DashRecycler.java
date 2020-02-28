package com.creater.shopping.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.creater.shopping.activity.CreateList;
import com.creater.shopping.activity.MainActivity;
import com.creater.shopping.R;
import com.creater.shopping.activity.shopping_fire_data_set;

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
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
     holder.imageView.setImageResource(data.get(position).getImg());
        holder.textView.setText(data.get(position).getValue());
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
        public Holder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.dashcard);
            textView=itemView.findViewById(R.id.dashText);
            imageView=itemView.findViewById(R.id.dashImage);
        }
    }
}
