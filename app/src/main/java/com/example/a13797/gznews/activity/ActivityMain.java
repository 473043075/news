package com.example.a13797.gznews.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.a13797.gznews.R;
import com.example.a13797.gznews.fragment.ForumFragment;
import com.example.a13797.gznews.fragment.HomeFragment;
import com.example.a13797.gznews.fragment.LocalFragment;
import com.example.a13797.gznews.fragment.MyFragment;

import com.example.a13797.gznews.method.setAndroidNativeLightStatusBar;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class ActivityMain extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{


    BottomNavigationBar bottomNavigationBar;
      private HomeFragment homeFragment;
      private LocalFragment localFragment;
      private ForumFragment forumFragment;
      private MyFragment myFragment;
    String imgPath,userName;


    @Override

    protected void onResume() {

// TODO Auto-generated method stub

        super.onResume();
        LinearLayout focus=(LinearLayout)findViewById(R.id.fragment_container);
        focus.setFocusable(true);

        focus.setFocusableInTouchMode(true);

        focus.requestFocus();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //隐藏ActionBar
        if (Build.VERSION.SDK_INT >= 21) {
            new setAndroidNativeLightStatusBar(ActivityMain.this,true);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        InitNavigationBar();

        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();

    }

    private void InitNavigationBar(){


        bottomNavigationBar = (BottomNavigationBar)findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar.setAutoHideEnabled(true);

        bottomNavigationBar
                .setActiveColor(R.color.red)
                .setInActiveColor(R.color.InActive)//默认未选择颜色
                .setBarBackgroundColor(R.color.white);//默认背景色，在colors.xml文件中定义

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.homepage_click,"首页")
//                        .setActiveColorResource(R.color.colorHome))
                ) .addItem(new BottomNavigationItem(R.drawable.local_click,"本地")
//                        .setActiveColorResource(R.color.colorlocal))
                ).addItem(new BottomNavigationItem(R.drawable.forum_click,"论坛")
//                        .setActiveColorResource(R.color.colorForum))
        )         .addItem(new BottomNavigationItem(R.drawable.me_click,"我")
//                        .setActiveColorResource(R.color.colorMe))
        )    .setFirstSelectedPosition(0)//设置默认选择的按钮
                .initialise();//所有的设置需在调用该方法前完成
    }

    /*
    * 设置默认的  设置首页home
    * */
    private  void setDefaultFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.replace(R.id.fragment_container,homeFragment);
        transaction.commit();
    }




    @Override
    public void onTabSelected(int position) {
        Log.d("onTabSelected", "onTabSelected: " + position);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                transaction.replace(R.id.fragment_container, homeFragment);
                break;
            case 1:
                if (localFragment == null) {
                    localFragment = new LocalFragment();
                }
                transaction.replace(R.id.fragment_container, localFragment);
                break;
            case 2:
                if (forumFragment == null) {
                    forumFragment = new ForumFragment();
                }
                transaction.replace(R.id.fragment_container, forumFragment);
                break;
            case 3:
                if (myFragment == null) {
                    myFragment =new MyFragment();
                }
                transaction.replace(R.id.fragment_container, myFragment);
                break;
            default:
                break;
        }

        transaction.commit();

    }

    @Override
    public void onTabReselected(int position) {
        Log.d("onTabUnselected", "onTabUnselected: " + position);
    }

    @Override
    public void onTabUnselected(int position) {
        Log.d("onTabReselected", "onTabReselected: " + position);
    }


}
