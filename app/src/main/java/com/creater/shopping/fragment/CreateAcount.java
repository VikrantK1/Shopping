package com.creater.shopping.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.creater.shopping.activity.Dashboard;
import com.creater.shopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAcount extends Fragment {
Activity activity;
EditText name,email,password,confirmPassword;
CardView submit;
TextView backlogin;
FirebaseAuth createAccount=FirebaseAuth.getInstance();
FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    public CreateAcount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_create_acount, container, false);
        name=v.findViewById(R.id.Name);
        email=v.findViewById(R.id.Email);
        password=v.findViewById(R.id.Password);
        confirmPassword=v.findViewById(R.id.ConfirmPassword);
        submit=v.findViewById(R.id.submitC);
        backlogin=v.findViewById(R.id.backToLogin);
        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.splash,new Login()).commit();
            }
        });
        submit();
        return v;
    }
    public void submit()
    {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameData=name.getText().toString().trim();
                final String emailData=email.getText().toString().trim();
                String passwordData=password.getText().toString().trim();
                String confirmData=confirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(nameData))
                {
                    name.setError("Name is required");
                    return;
                }
                if (TextUtils.isEmpty(emailData))
                {
                    email.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty(passwordData))
                {
                    password.setError("Password is required");
                    return;
                }
                if (passwordData.length()<6)
                {
                    password.setError("Password should be greater than 6 Characters");
                }
                if (TextUtils.isEmpty(confirmData))
                {
                    confirmPassword.setError("Confirm Password is required");
                    return;
                }
                if (TextUtils.isEmpty(nameData))
                {
                    name.setError("Name is required");
                    return;
                }
                if (!confirmData.equals(passwordData))
                {
                    confirmPassword.setError("Confirm password is not matching");
                    return;
                }

             createAccount.createUserWithEmailAndPassword(emailData,passwordData).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Map<String,String > data=new HashMap<>();
                        data.put("UserId",createAccount.getCurrentUser().getUid());
                        data.put("Name",nameData);
                        data.put("Email",emailData);
                        data.put("Admin","no");
                        firebaseFirestore.collection("User").document(createAccount.getCurrentUser().getUid()).set(data);
                        startActivity(new Intent(activity, Dashboard.class));
                    }
                    else
                    {
                        Toast.makeText(activity,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    }
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Toast.makeText(activity,"Somthing is wrong",Toast.LENGTH_SHORT).show();
                 }
             });
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity= (Activity) context;
    }
}
