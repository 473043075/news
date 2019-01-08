package com.example.a13797.gznews.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13797.gznews.R;

import com.example.a13797.gznews.method.DbDao;
import com.example.a13797.gznews.method.setAndroidNativeLightStatusBar;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ImageView concerImg,pwdear;
    private EditText et_uesename,et_password;
    private Button loginBtn;
    private CheckBox checkPassword,checkLogin;
    private TextView registerBtn;
    private List<String> list1=new ArrayList<>();
    private List<String> list2=new ArrayList<>();
    DbDao db;
    private static final int LOGIN_IN_SUCESS = 2;
    private static final int LOGIN_IN_FAILED = 3;




    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_login){
            final String userid = et_uesename.getText().toString().trim();
            final String userpassword = et_password.getText().toString().trim();
            if (!userid.equals("")&&!userpassword.equals("")) {
                Loading();

                new Thread(new Runnable() {//配合Handler，开启线程，调用数据库方法验证用户名和密码，可行
                    @Override
                    public void run() {
                        db = new DbDao();
                        final Message msg = new Message();
                        final boolean is = db.validateUserLogin(userid, userpassword);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String userName = db.getUersNameFromid(userid);
                                String imgPath = db.readDB2Image(userid);
                                if (is) {
                                    msg.what = LOGIN_IN_SUCESS;
                                    msg.obj = userName+";"+imgPath;

                                    handler.sendMessage(msg);
                                } else {
                                    msg.what = LOGIN_IN_FAILED;
                                    handler.sendMessage(msg);
                                }
                            }
                        });



                    }
                }).start();

            }else
                Toast.makeText(ActivityLogin.this,"账号或者密码不可为空",Toast.LENGTH_SHORT).show();

        }
        if(v.getId()==R.id.login_img_concer){
           finish();
        }
        if (v.getId()==R.id.login_regiter_btn){
            Intent intent = new Intent(ActivityLogin.this,ActivityRegister.class);
            startActivity(intent);
        }
        if (v.getId()==R.id.login_img_password_ear){

            if (pwdear.isSelected()) {
                pwdear.setSelected(false);
                pwdear.setImageResource(R.drawable.ear);
                //密码不可见
                et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            } else {
                pwdear.setSelected(true);
                pwdear.setImageResource(R.drawable.closeear);
                //密码可见
                et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }


        }

        if (v.getId()==R.id.checkBox_password){

        }

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOGIN_IN_SUCESS:
                    Toast.makeText(ActivityLogin.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ActivityLogin.this, ActivityMain.class);
                    String str1=(String)msg.obj;
                    String[] array = str1.split(";");

                        String username = array[0];
                        String imaPath = array[1];
                        Log.d("str1",username+imaPath);

                    intent.putExtra("imgPath",username);
                    intent.putExtra("username",imaPath);
                    startActivity(intent);
                    finish();
//                    Log.d("tag-login","success");
                    break;
                case LOGIN_IN_FAILED:
                    Toast.makeText(ActivityLogin.this,"登录失败，请重新登录",Toast.LENGTH_SHORT).show();
                    et_password.setText("");
                    et_uesename.setText("");
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        setupEvents();
        //隐藏ActionBar
        if (Build.VERSION.SDK_INT >= 21) {
            new setAndroidNativeLightStatusBar(ActivityLogin.this,true);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();




    }
    private void initViews(){
        concerImg=(ImageView)findViewById(R.id.login_img_concer);
        pwdear = (ImageView)findViewById(R.id.login_img_password_ear);
        et_password = (EditText)findViewById(R.id.login_txt_password);
        et_uesename = (EditText)findViewById(R.id.login_txt_user);
        loginBtn = (Button)findViewById(R.id.btn_login);
        checkPassword = (CheckBox)findViewById(R.id.checkBox_password);
        checkLogin = (CheckBox)findViewById(R.id.checkBox_login);
        registerBtn = (TextView)findViewById(R.id.login_regiter_btn);

    }
    private void setupEvents(){
        concerImg.setOnClickListener(this);
        checkLogin.setOnClickListener(this);
        checkPassword.setOnClickListener(this);
        pwdear.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        et_uesename.setText(getIntent().getStringExtra("userid"));
    }


    //Loading
    private void Loading() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.loading, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }



}
