package com.creater.shopping.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.creater.shopping.R;
import com.creater.shopping.activity.final_list;
import com.creater.shopping.activity.savedList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SaveAdapter extends ArrayAdapter {
    AlertDialog.Builder builder;
    AlertDialog dialog;
    Context mcotext;
    ArrayList<String> data;

    public SaveAdapter(@NonNull Context context, @NonNull ArrayList<String> objects) {
        super(context, R.layout.savelistrow, objects);
        this.mcotext = context;
        this.data = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v;
        LayoutInflater inflater = (LayoutInflater) mcotext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.savelistrow, parent, false);

        TextView listname = v.findViewById(R.id.listName);
        final RelativeLayout listrow = v.findViewById(R.id.saveRow);
        final TextView date = v.findViewById(R.id.listDate);
        listname.setText(data.get(position));
        if (TextUtils.isEmpty(date.getText())) {
            listrow.setClickable(false);
        }
        firebasehelp.store.collection("User").document(firebasehelp.auth.getCurrentUser()
                .getUid()).collection("List").document(data.get(position))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                ArrayList<String> dat = (ArrayList<String>) doc.get("ListData");
                date.setText(dat.get(0));
                listrow.setClickable(true);
                dat.remove(0);
                final ArrayList<String> list = dat;
                listrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mcotext, final_list.class);
                        intent.putStringArrayListExtra("ShoppingList", list);
                        mcotext.startActivity(intent);
                    }
                });
                listrow.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        diloge(position);
                        return false;
                    }
                });

            }
        });

        return v;
    }

    public void diloge(final int pos) {
        builder = new AlertDialog.Builder(mcotext).setIcon(R.drawable.delete).setMessage("Delete the Data")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mcotext);
                        final AlertDialog dialog23 = builder.create();
                        LayoutInflater inflater = (LayoutInflater) mcotext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View v = inflater.inflate(R.layout.progessdiloge, null, false);
                        dialog23.setView(v);
                        dialog23.show();
                        firebasehelp.store.collection("User").document(firebasehelp.auth.getCurrentUser().getUid())
                                .collection("List").document(data.get(pos)).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                data.remove(pos);
                                dialog23.dismiss();
                                notifyDataSetChanged();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mcotext, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

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
