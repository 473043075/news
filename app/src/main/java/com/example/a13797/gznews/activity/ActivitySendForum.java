package com.example.a13797.gznews.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a13797.gznews.R;
import com.example.a13797.gznews.method.getPhotoFromPhotoAlbum;
import com.example.a13797.gznews.method.setAndroidNativeLightStatusBar;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class ActivitySendForum extends AppCompatActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks{

    private TextView sendForum,cancelForum;
    private ImageView photoImg,ivTest;

    private File cameraSavePath;//拍照照片路径
    private String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private Uri uri;//照片uri

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_forum);
        //隐藏ActionBar
        if (Build.VERSION.SDK_INT >= 21) {
            new setAndroidNativeLightStatusBar(ActivitySendForum.this, true);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        setEvents();
        isGrantExternalRW(this);
        getPermission();


    }

    public void initView(){
        sendForum = (TextView)findViewById(R.id.send_forum_send);
        cancelForum = (TextView)findViewById(R.id.send_forum_cancel);
        photoImg = (ImageView)findViewById(R.id.send_forum_photo);
        ivTest=(ImageView)findViewById(R.id.iv_test);

    }

    public void setEvents(){
        sendForum.setOnClickListener(this);
        cancelForum.setOnClickListener(this);
        photoImg.setOnClickListener(this);
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");





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
            uri = FileProvider.getUriForFile(ActivitySendForum.this, "com.example.hxd.pictest.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        ActivitySendForum.this.startActivityForResult(intent, 1);
    }





    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.send_forum_cancel){
            finish();
        }
        if (v.getId()==R.id.send_forum_send){}
        if (v.getId()==R.id.send_forum_photo){
            showBottomDialog();
        }
    }




    private void showBottomDialog(){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this,R.layout.activity_send_forum_belowdialog,null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.open_from_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               goCamera();
               dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.open_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goPhotoAlbum();
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.activity_send_forum_rela).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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
        String photoPath;
        if (requestCode == 1 && resultCode == RESULT_OK) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }
            Log.d("拍照返回图片路径:", photoPath);
            Glide.with(ActivitySendForum.this).load(photoPath).into(ivTest);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            Glide.with(ActivitySendForum.this).load(photoPath).into(ivTest);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    //获取权限
    private void getPermission(){
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
//            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
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

        Toast.makeText(this, "相关权限获取成功", Toast.LENGTH_SHORT).show();
    }
    //用户未同意权限
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "请同意相关权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
    }





}
