package com.creater.shopping.util;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.creater.shopping.R;

import java.util.ArrayList;

public class CreateRecycler extends RecyclerView.Adapter<CreateRecycler.Holder> {
    Context mcontext;
    ArrayList<String > list23;
 public  ArrayList<String>created=new ArrayList<>();
  public CreateRecycler()
  {

  }
    public CreateRecycler(Context context, ArrayList<String> list)
    {
        this.mcontext=context;
        this.list23=list;
    }
    @NonNull
    @Override
    public CreateRecycler.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.createlistrow,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CreateRecycler.Holder holder, final int position) {
    holder.textView.setText(list23.get(position));
    holder.cardView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
               if (!created.contains(list23.get(position)))
               {
                   created.add(list23.get(position));
               }
               holder.imageView.setVisibility(View.VISIBLE);
               holder.cardView.setCardBackgroundColor(Color.parseColor("#7791e0"));
               holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    created.remove(list23.get(position));
                    holder.imageView.setVisibility(View.INVISIBLE);
                    holder.cardView.setCardBackgroundColor(Color.WHITE);
                }
            });
        }
    });

    }

    @Override
    public int getItemCount() {
        return list23.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
     TextView textView;
     CardView cardView;
     ImageView imageView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.CreateData);
            cardView=itemView.findViewById(R.id.Createcard);
            imageView=itemView.findViewById(R.id.removeFromCart);
        }
    }
}
