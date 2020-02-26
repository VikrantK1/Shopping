package com.creater.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
AutoCompleteTextView text;
Button submit;
ListView shopinglist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initilation();
    }
    public void initilation()
    {
        text=findViewById(R.id.addList);
        submit=findViewById(R.id.submit);
        shopinglist=findViewById(R.id.shoppingList);
    }
    public void editTextFun()
    {

    }
}
