package com.example.a13797.gznews.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.a13797.gznews.R;
import com.example.a13797.gznews.method.setAndroidNativeLightStatusBar;

public class ActivitySearch extends AppCompatActivity implements View.OnClickListener {
    private TextView cancelBrn;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //隐藏ActionBar
        if (Build.VERSION.SDK_INT >= 21) {
            new setAndroidNativeLightStatusBar(ActivitySearch.this,true);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initview();
        setEvents();
    }
    private void initview(){
        cancelBrn = (TextView)findViewById(R.id.activity_search_cancel);
        searchView = (SearchView)findViewById(R.id.activity_searchview);
    }
    private void setEvents(){
        cancelBrn.setOnClickListener(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.activity_search_cancel){
            finish();
        }
    }

}
