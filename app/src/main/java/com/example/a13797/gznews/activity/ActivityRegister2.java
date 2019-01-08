package com.example.a13797.gznews.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a13797.gznews.R;
import com.example.a13797.gznews.entity.User;
import com.example.a13797.gznews.method.DbDao;
import com.example.a13797.gznews.method.SexBox;
import com.example.a13797.gznews.method.getPhotoFromPhotoAlbum;
import com.example.a13797.gznews.method.setAndroidNativeLightStatusBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.EasyPermissions;

public class ActivityRegister2 extends AppCompatActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks {
    private ImageView registerscancel;
    private EditText register2Name,register2Email,register2Phonenum;
    SexBox sexBox;
    Button register2Btn;
    CircleImageView circleImageView;
    private Uri uri;//照片uri
    private File cameraSavePath;//拍照照片路径
    String photoPath;
    Dialog dialog1;
    private String[] permissions = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    String id,password,name,phonenum,email,sex,registertime;
    DbDao db;
    User user;
    private static final int ADD_USER_SUCCESS = 1;
    private static final int CHOOSE_ALBUM = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        if (Build.VERSION.SDK_INT >= 21) {
            new setAndroidNativeLightStatusBar(ActivityRegister2.this, true);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initview();
        setEvents();
        getPermission();
        isGrantExternalRW(this);
    }

 private void initview(){
        registerscancel = (ImageView)findViewById(R.id.register2_img_concer);
        sexBox=(SexBox) findViewById(R.id.sex);
        register2Btn=(Button)findViewById(R.id.btn_register2);
        circleImageView = (CircleImageView) findViewById(R.id.register2_image);
        register2Name = (EditText)findViewById(R.id.register2_name);
        register2Phonenum =(EditText)findViewById(R.id.register2_phonenum);
        register2Email = (EditText)findViewById(R.id.register2_email);
 }
private  void setEvents(){
        registerscancel.setOnClickListener(this);
        register2Btn.setOnClickListener(this);
        circleImageView.setOnClickListener(this);
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");


}

    @Override
    public void onClick(View v) {
        user = new User();
        Intent intent=getIntent();
        id = intent.getStringExtra("id");
        password=intent.getStringExtra("password");
        if (v.getId()==R.id.register2_img_concer){
            finish();
        }

        if (v.getId()==R.id.register2_image){
            showBottomDialog();
        }
        if (v.getId()==R.id.btn_register2) {

                //性别
                switch (sexBox.getstatu()) {
                    case 0:
                        sex = "未选择";
                        break;
                    case 1:
                        sex = "男";
                        break;
                    case 2:
                        sex = "女";
                        break;
                    default:
                        sex = "错误";
                        break;
                }
                //  Toast.makeText(ActivityRegister2.this,sex,Toast.LENGTH_SHORT).show();

                //时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                registertime = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
//            SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");//根据注册时间生成用户id，需要字段类型nvarchar(50)
//            final String userIdStr = df1.format(new Date()) + id;

                name = register2Name.getText().toString().trim();
                phonenum = register2Phonenum.getText().toString().trim();
                email = register2Email.getText().toString().trim();

            if (name.equals("") || phonenum.equals("") || email.equals("") || sex.equals("未选择")) {
                Toast.makeText(ActivityRegister2.this, "请填写相关信息", Toast.LENGTH_SHORT).show();
            } else {
                user.setUsersId(id);
                user.setUsersPassword(password);
                user.setUsersName(name);
                user.setUserSex(sex);
                user.setUsersRegistertime(registertime);
                user.setUserPhonenum(phonenum);
                user.setUserEmail(email);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db = new DbDao();
                        String isRight = db.isLoginUpRight(name, phonenum, email);
                        if (isRight.length() > 13) {
                            showMessage(isRight);
                            dialog1.dismiss();
                        } else {
                            db.addUser(user);
                            db.readImage2DB(photoPath, id);
                            Message msg = new Message();
                            msg.what = ADD_USER_SUCCESS;
                            handler.sendMessage(msg);
                        }
                    }
                }).start();
                Loading();
            }
        }

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADD_USER_SUCCESS:
                    db.readImage2DB(photoPath, id);
                    Intent intent = new Intent(ActivityRegister2.this, ActivityLogin.class);
                    intent.putExtra("userid", id);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    void showMessage(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ActivityRegister2.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            }, 1);
            return false;
        }

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }
            Log.d("拍照返回图片路径:", photoPath);
            Glide.with(ActivityRegister2.this).load(photoPath).into(circleImageView);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            Glide.with(ActivityRegister2.this).load(photoPath).into(circleImageView);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //获取权限
    private void getPermission(){
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //框架要求必须这么写
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    //成功打开权限
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

//        Toast.makeText(this, "相关权限获取成功", Toast.LENGTH_SHORT).show();
    }
    //用户未同意权限
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "请同意相关权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
    }
//底部弹框
    private void showBottomDialog(){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this,R.layout.register2diolage,null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.register2_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goCamera();
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.register2_open_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goPhotoAlbum();
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.register2_cancer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.activity_register2_rela).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    //激活相机操作
    private void goCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(ActivityRegister2.this, "com.example.hxd.pictest.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        ActivityRegister2.this.startActivityForResult(intent, 1);
    }


    private void Loading() {
        //1、使用Dialog、设置style
        dialog1 = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.loading, null);
        dialog1.setContentView(view);
        dialog1.setCancelable(false);
        Window window = dialog1.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog1.show();
    }
//    //图片压缩
//    private Bitmap compressPixel(String filePath){
//        Bitmap bmp = null;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        //setting inSampleSize value allows to load a scaled down version of the original image
//        options.inSampleSize = 2;
//
//        //inJustDecodeBounds set to false to load the actual bitmap
//        options.inJustDecodeBounds = false;
//        options.inTempStorage = new byte[16 * 1024];
//        try {
//            //load the bitmap from its path
//            bmp = BitmapFactory.decodeFile(filePath, options);
//            if (bmp == null) {
//
//                InputStream inputStream = null;
//                try {
//                    inputStream = new FileInputStream(filePath);
//                    BitmapFactory.decodeStream(inputStream, null, options);
//                    inputStream.close();
//                } catch (FileNotFoundException exception) {
//                    exception.printStackTrace();
//                } catch (IOException exception) {
//                    exception.printStackTrace();
//                }
//            }
//        } catch (OutOfMemoryError exception) {
//            exception.printStackTrace();
//        }finally {
//            return bmp;
//        }
//
//
//
//    }
//





}
