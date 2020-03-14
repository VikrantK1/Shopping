package com.creater.shopping.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.creater.shopping.R;

import java.util.ArrayList;

public class ArrAdapter extends ArrayAdapter {
    ArrayList<String> data;
    Context mcontext;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    public ArrAdapter(@NonNull Context context, ArrayList<String> objects) {
        super(context, R.layout.shopinglist, objects);
        this.data = objects;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.shopinglist, parent, false);
        TextView textView = v.findViewById(R.id.shopping_data);
        textView.setText(data.get(position));
        CardView cardView = v.findViewById(R.id.shopping_card);
        builder = new AlertDialog.Builder(mcontext);
        builder.setIcon(R.drawable.delete);
        builder.setCancelable(false);
        builder.setTitle("Delete").setMessage("Do you want to Delete from list")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        diloge(position);
                        notifyDataSetChanged();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        return v;
    }

    public void diloge(final int pos) {
        builder = new AlertDialog.Builder(mcontext).setIcon(R.drawable.delete).setMessage("Delete the Data")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data.remove(pos);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog = builder.create();
        dialog.show();
    }
}
