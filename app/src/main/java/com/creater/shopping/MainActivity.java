package com.creater.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
AutoCompleteTextView text;
Button submit;
ListView shopinglist;
String [] list={"vikr","vata","meta","mata","sata"};
ArrAdapter arrAdapter;
ImageView addbtn;
ArrayList<String> shopping=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initilation();
        editTextFun();
        addToList();
    }
    public void initilation()
    {
        text=findViewById(R.id.addList);
        submit=findViewById(R.id.submit);
        shopinglist=findViewById(R.id.shoppingList);
        addbtn=findViewById(R.id.addbtn);
        arrAdapter=new ArrAdapter(MainActivity.this,shopping);
        shopinglist.setAdapter(arrAdapter);
    }
    public void editTextFun()
    {
        ArrayAdapter<String> ad=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,list);
        text.setAdapter(ad);
        text.setThreshold(1);
    }
    public void addToList()
    {
    addbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!shopping.contains(text.getText().toString()))
            {
                shopping.add(text.getText().toString());
                Toast.makeText(getApplicationContext(),"Add to Shopping list",Toast.LENGTH_SHORT).show();
                arrAdapter.notifyDataSetChanged();

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Added before in list",Toast.LENGTH_SHORT).show();

            }
            text.setText("");
        }

    });
    }
}
