package com.creater.shopping.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.creater.shopping.R;
import com.creater.shopping.activity.Dashboard;
import com.creater.shopping.util.SaveAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static java.util.Objects.requireNonNull;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaveListData extends Fragment {
    Activity activity;
    ListView listView;
    FirebaseFirestore store = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    SaveAdapter saveAdapter;
    ArrayList<String> list = new ArrayList<>();

    public SaveListData() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_save_list_data, container, false);
        listView = v.findViewById(R.id.listview);
        saveAdapter = new SaveAdapter(activity, list);
        listView.setAdapter(saveAdapter);
        listgenerate();
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    public void listgenerate() {
        store.collection("User").document(auth.getCurrentUser().getUid()).collection("List").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot doc : task.getResult()
                ) {
                    list.add(doc.getId());
                    //Collections.reverse(list);
                    saveAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }


}
