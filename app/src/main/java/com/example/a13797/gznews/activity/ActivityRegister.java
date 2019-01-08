package com.example.a13797.gznews.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a13797.gznews.R;

//import com.example.a13797.gznews.method.DatabaseOperation;
import com.example.a13797.gznews.entity.User;
import com.example.a13797.gznews.method.setAndroidNativeLightStatusBar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityRegister extends AppCompatActivity implements View.OnClickListener {
    private ImageView cencelImg;
    private EditText et_phone, et_pwd1, et_pwd2,et_name;
    private TextInputLayout til_phone;
    private Button registerBtn;
    String usersid ;
    String pwd1  ;
    String pwd2 ;
    String registertime;
    User user;
//    DatabaseOperation databaseOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //隐藏ActionBar
        if (Build.VERSION.SDK_INT >= 21) {
            new setAndroidNativeLightStatusBar(ActivityRegister.this, true);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initviews();
        setEvents();


        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                til_phone.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    public void initviews() {
        cencelImg = (ImageView) findViewById(R.id.register_img_concer);
        et_phone = (EditText) findViewById(R.id.register_ph);
        et_pwd1 = (EditText) findViewById(R.id.register_pwd1);
        et_pwd2 = (EditText) findViewById(R.id.register_pwd2);
        til_phone = (TextInputLayout) findViewById(R.id.register_ph_til);
        registerBtn = (Button) findViewById(R.id.btn_register);

    }

    public void setEvents() {
        cencelImg.setOnClickListener(this);
        registerBtn.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register_img_concer) {
            finish();
        }
        if (v.getId() == R.id.btn_register) {
            doRegister();
        }
    }




    private void doRegister() {
        usersid = et_phone.getText().toString().trim();
        pwd1 = et_pwd1.getText().toString().trim();
        pwd2 = et_pwd2.getText().toString().trim();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        registertime = formatter.format(curDate);


           user  = new User();
           user.setUsersId(usersid);
           user.setUsersPassword(pwd1);
           user.setUsersRegistertime(registertime);





            if (usersid.length() != 11&&usersid!="") {
                til_phone.setErrorEnabled(true);
                til_phone.setError("请输入正确的手机号码！");
                return;
            }
            if (pwd1.length() < 6&&pwd1!="") {
                Toast.makeText(ActivityRegister.this
                        , "请输入6位以上的密码！"
                        , Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            if (pwd1.equals(pwd2)) {
                Intent intent = new Intent(ActivityRegister.this, ActivityRegister2.class);
                intent.putExtra("id",usersid);
                intent.putExtra("password",pwd1);
                startActivity(intent);

            } else {
                Toast.makeText(ActivityRegister.this
                        , "两次密码不一致！"
                        , Toast.LENGTH_SHORT)
                        .show();
                return;

            }

        }
    }


