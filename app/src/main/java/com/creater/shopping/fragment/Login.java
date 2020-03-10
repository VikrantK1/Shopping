package com.creater.shopping.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.creater.shopping.activity.DashBoardAdmin;
import com.creater.shopping.activity.Dashboard;
import com.creater.shopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

Activity mactivity;

EditText email,password;
TextView createAccount;
Button login;
FirebaseAuth authLogin=FirebaseAuth.getInstance();
FirebaseFirestore store=FirebaseFirestore.getInstance();
    public Login() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_login, container, false);

        mactivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        email=v.findViewById(R.id.emailLogin);
        password=v.findViewById(R.id.password_Login);
        createAccount=v.findViewById(R.id.goToCreateAccount);
        login=v.findViewById(R.id.login);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.splash,new CreateAcount()).commit();
            }
        });
        login();
        return v;
    }

public void login()
{
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        String emaildata=email.getText().toString().trim();
        String passwordData=password.getText().toString().trim();
        if (TextUtils.isEmpty(emaildata))
        {
            email.setError("Enter the emailAddress");
            return;
        }
        if (TextUtils.isEmpty(passwordData))
        {
            password.setError("Enter the Password");
            return;
        }
        if (passwordData.length()<6)
        {
            password.setError("Password should be Greater than 6");
            return;
        }
           AlertDialog.Builder builder=new AlertDialog.Builder(mactivity);
            final AlertDialog dialog=builder.create();
            dialog.setView(getLayoutInflater().inflate(R.layout.progessdiloge,null,false));
            dialog.show();

        authLogin.signInWithEmailAndPassword(emaildata,passwordData).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    final Intent intent=new Intent(mactivity, Dashboard.class);
                    store.collection("User").document(authLogin.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot data=task.getResult();
                            if (data.getString("Admin").equals("yes"))
                            {
                                dialog.dismiss();
                                startActivity(new Intent(mactivity, DashBoardAdmin.class));
                            }
                            else if (data.getString("Admin").equals("no"))
                            {
                                dialog.dismiss();
                                startActivity(intent);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mactivity,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(mactivity,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(mactivity,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        }
    });
}
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mactivity= (Activity) context;
    }
}
