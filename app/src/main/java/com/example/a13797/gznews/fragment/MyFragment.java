package com.example.a13797.gznews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a13797.gznews.activity.ActivityLogin;
import com.example.a13797.gznews.R;
import com.example.a13797.gznews.activity.ActivityRegister2;
import com.example.a13797.gznews.activity.ActivitySendForum;
import com.leon.lib.settingview.LSettingItem;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFragment extends Fragment implements View.OnClickListener {
    LSettingItem recordItem,collectItem,commentItem,settingItem;

    CircleImageView  my_login_image;
//    TextView tiShi;
//    String imgPath,userName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_layout_frag,container,false); 
        return view;

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.my_login_image){
            Intent intent = new Intent(getActivity(),ActivityLogin.class);
            startActivity(intent);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        my_login_image = (CircleImageView)getActivity().findViewById(R.id.my_login_image);
        my_login_image.setOnClickListener(this);
//        tiShi = (TextView)getActivity().findViewById(R.id.my_tishi);
//        my_login_image.setOnClickListener(this);
//        Intent intent = getActivity().getIntent();
//        imgPath=intent.getStringExtra("imgPath");
//        userName=intent.getStringExtra("username");


        recordItem= (LSettingItem) getActivity().findViewById(R.id.record_item);
        recordItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {

            }
        });
        collectItem= (LSettingItem) getActivity().findViewById(R.id.collect_item);
        collectItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {

            }
        });
        commentItem= (LSettingItem) getActivity().findViewById(R.id.comments_item);
        commentItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {

            }
        });
        settingItem= (LSettingItem) getActivity().findViewById(R.id.setting_item);
        settingItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {

            }
        });
    }



}
