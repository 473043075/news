package com.example.a13797.gznews.activity;

import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.a13797.gznews.R;
import com.example.a13797.gznews.method.setAndroidNativeLightStatusBar;

public class ActivityNewsplay extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsplay);
        if (Build.VERSION.SDK_INT >= 21) {
            new setAndroidNativeLightStatusBar(ActivityNewsplay.this, true);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initview();
        setEvent();
    }
    void initview(){
        back = (ImageView)findViewById(R.id.newsplay_img_concer);


    }

    void setEvent(){
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newsplay_img_concer:
                finish();
                break;
                default:
                    break;
        }

    }
}
