package com.creater.shopping.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.creater.shopping.R;

import java.util.ArrayList;
import java.util.Collections;

public class ListCreating extends RecyclerView.Adapter<ListCreating.Holder> {
    AlertDialog.Builder builder;
    Context mcontext;
    ArrayList<String > data;
    AlertDialog dialog;

    public ListCreating(Context context, ArrayList<String > list)
    {
        this.mcontext=context;
        this.data=list;

    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.shopinglist,parent,false);

        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
    holder.textView.setText(data.get(position));
        final int data23=position;
    holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

            Toast.makeText(mcontext,data.get(position)+"is Deleted",Toast.LENGTH_SHORT).show();
            diloge(position);

            return false;
        }
    });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;
        public Holder(@NonNull View itemView) {
            super(itemView);
              textView=itemView.findViewById(R.id.shopping_data);
             cardView=itemView.findViewById(R.id.shopping_card);
        }
    }
    public void  diloge(final int pos)
    {
        builder=new AlertDialog.Builder(mcontext).setIcon(R.drawable.delete).setMessage("Delete the Data")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data.remove(pos);
                        notifyDataSetChanged();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog=builder.create();
        dialog.show();
    }
}
