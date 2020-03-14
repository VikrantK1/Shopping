package com.creater.shopping.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.creater.shopping.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    Context mcontext;
    ArrayList<Helper> data;

    public RecyclerAdapter(Context context, ArrayList<Helper> list) {
        this.mcontext = context;
        this.data = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.finallistrow, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.Heading.setText(data.get(position).getProduct_Name());
        holder.desc.setText(data.get(position).getDiscription());
        holder.floor.setText(data.get(position).getFloor());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView Heading,desc,floor;

        public Holder(@NonNull View itemView) {
            super(itemView);
            Heading = itemView.findViewById(R.id.finalHead);
            desc = itemView.findViewById(R.id.finalDesc);
            floor=itemView.findViewById(R.id.floorNo);
        }
    }

}
