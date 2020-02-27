package com.creater.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.creater.shopping.util.DashContener;
import com.creater.shopping.util.DashRecycler;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
String name[]={"Create List","Having List","Saved List"};
int img[]={R.drawable.ic_shopping_cart_black_24dp,R.drawable.list,R.drawable.saved};
ArrayList<DashContener> list=new ArrayList<>();
TextView textView;
RecyclerView recyclerView;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recyclerView=findViewById(R.id.dashRecycler);
        list.add(new DashContener(name[0],img[0]));
        list.add(new DashContener(name[1],img[1]));
        list.add(new DashContener(name[2],img[2]));
        toolbar=findViewById(R.id.toolbar);
        getSupportActionBar().hide();
        setActionBar(toolbar);
        DashRecycler dashRecycler=new DashRecycler(this,list);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(10,10,10,10);
            }
        });
        recyclerView.setAdapter(dashRecycler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
