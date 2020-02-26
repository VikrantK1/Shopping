package com.creater.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
ArrayList<String> shopping=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initilation();
        editTextFun();
    }
    public void initilation()
    {
        text=findViewById(R.id.addList);
        submit=findViewById(R.id.submit);
        shopinglist=findViewById(R.id.shoppingList);
        arrAdapter=new ArrAdapter(getApplicationContext(),shopping);
    }
    public void editTextFun()
    {
        ArrayAdapter<String> ad=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,list);
        text.setAdapter(ad);
        text.setThreshold(1);
        text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s1= (String) parent.getSelectedItem();
                Toast.makeText(getApplicationContext(),list[position],Toast.LENGTH_SHORT).show();
                shopping.add(list[position]);
                arrAdapter.notifyDataSetChanged();
            }
        });
    }
}
