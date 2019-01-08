package com.example.a13797.gznews.method;

import android.os.Environment;
import android.util.Log;

import com.example.a13797.gznews.activity.ActivityRegister2;
import com.example.a13797.gznews.entity.User;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/12/9.
 * 所有对数据库表的操作方法都写在该类中
 */

public class DbDao {
    private Connection conn;
    private PreparedStatement ptmt;
    private DbConnection dbConnection;
    private ResultSet resultSet;
    List<User> users;

    public DbDao() {
        dbConnection = new DbConnection();
        conn = dbConnection.getConnection();
        users = new ArrayList<>();
    }






    //向user表里面插入数据
    public String addUser(User user) {
        String sql = "insert into users(users_id,users_password,users_name,users_registertime,users_phonenum,users_sex,users_email)" +
                " values(?,?,?,?,?,?,?)";
        String userId = "";

        try {
            ptmt = (PreparedStatement) conn.prepareStatement(sql);
            userId = user.getUsersId();

            ptmt.setString(1, user.getUsersId());
            ptmt.setString(3, user.getUsersName());
            ptmt.setString(2, user.getUsersPassword());
            ptmt.setString(4, user.getUsersRegistertime());
//            ptmt.setBinaryStream(5, user.getUserImg());
            ptmt.setString(5, user.getUserPhonenum());
            ptmt.setString(6, user.getUserSex());
            ptmt.setString(7, user.getUserEmail());

            // 在此 PreparedStatement 对象中执行 SQL 语句
            ptmt.execute();
            users.add(user);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            dbConnection.closeConn();
            return userId;
        }
    }

    //验证注册时用户名、电话号码、邮箱是否格式正确以及是否已经注册过
    public String isLoginUpRight(String userid, String phone, String email) {
        List<User> users = queryUsers();
        String repeatedForUser = "";
        String invalidateForUser="";
        boolean isEmail,isPhone;
        if ((isEmail = Pattern.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+", email)) == false) {
            invalidateForUser += " 邮箱  ";
        }
        if ((isPhone = Pattern.matches("^1[3578]+\\d{9}$", phone)) == false) {
            invalidateForUser += " 电话号码 ";
        }

        if(users.size()>1) {
            for (User element : users) {
                if (element.getUsersId().equals(userid)) {
                    repeatedForUser += " 用户名 ";
                }
                if (element.getUserEmail().equals(email)) {
                    repeatedForUser += " 邮箱 ";
                }
                if (element.getUserPhonenum().equals(phone)) {
                    repeatedForUser += " 电话号码 ";
                }
            }
        }
        return repeatedForUser+"已被注册\n"+invalidateForUser+"格式不符合要求";


    }

    //验证用户登录名和密码
    public boolean validateUserLogin(String userid, String password) {

        try {
            String sql1 = "select * from users";

            ptmt = (PreparedStatement) conn.prepareStatement(sql1);
            resultSet = ptmt.executeQuery();
            while (resultSet.next()) {
                Log.d("tag-validate", resultSet.getString(2) + "\t" + resultSet.getString(1));
                if (resultSet.getString(2).equals(password) && resultSet.getString(1).equals(userid)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConn();
        }
        return false;
    }

    //遍历用户users表存入List
    List<User> queryUsers() {
        User user = null;
        String sql = "select * from users";
        try {
            ptmt = (PreparedStatement) conn.prepareStatement(sql);
            resultSet = ptmt.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setUsersId(resultSet.getString(1));
                user.setUsersPassword(resultSet.getString(2));
                user.setUsersName(resultSet.getString(3));
//                user.setUserImg(resultSet.getString(5));
                user.setUsersRegistertime(resultSet.getString(4));
                user.setUserSex(resultSet.getString(6));
                user.setUserPhonenum(resultSet.getString(5));
                user.setUserEmail(resultSet.getString(7));
                users.add(user);
//                Log.d("tag-queryusersdao",user.getUsersName()+"\t"+user.getUserPhonenum()+"\t"+user.getUserEmail()+"\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    //保存图片到user_image表
    public void readImage2DB(String imgPath, String userId) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(imgPath));
//            String sql = "insert into users (userz_img)values(?)";
            String sql = "update users set users_img=? where users_id=?";
            ptmt = (PreparedStatement) conn.prepareStatement(sql);
            ptmt.setString(2, userId);
            ptmt.setBinaryStream(1, in, in.available());
            int count = ptmt.executeUpdate();
            if (count > 0) {
                Log.d("tag-img-todb","插入成功！");
            } else {
                Log.d("tag-img-todb","插入成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConn();
        }
    }
    //通过id获取用户昵称
    public String getUersNameFromid(String userid){
        String userName = null;

        try {
            String sql = "select * from users where users_id = ?";
            ptmt = (PreparedStatement) conn.prepareStatement(sql);
            ptmt.setString(1, userid);
            resultSet = ptmt.executeQuery();
//            if(resultSet!=null) {
                while (resultSet.next()) {
                    userName = resultSet.getString(2);
                    Log.d("getUernameFromid", userName);

                }
//            }else{
//                Log.d("tag-username","空");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConn();
            return userName;
        }

    }





    //读取数据库图片
    public String  readDB2Image(String userid) {
        String targetPath = null;

        try {
            String sql = "select * from users where users_id =?";
            ptmt = (PreparedStatement) conn.prepareStatement(sql);
            ptmt.setString(1,userid);
            resultSet = ptmt.executeQuery();
            while (resultSet.next()) {
                InputStream in = resultSet.getBinaryStream("users_img");
                if(in!=null) {
                    targetPath = readBin2Image(in);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConn();
            return targetPath;
        }
    }
    //根据用户id获取图片
//    public String  readDB2Image1(String userid) {
//        String targetPath = null;
//
//        try {
//            String sql = "select * from users where users_id =?";
//            ptmt = (PreparedStatement) conn.prepareStatement(sql);
//            ptmt.setString(1, userid);
//            resultSet = ptmt.executeQuery();
//            while (resultSet.next()) {
//                InputStream in = resultSet.getBinaryStream("users_img");
//                if(in!=null) {
//                    targetPath = readBin2Image(in);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            dbConnection.closeConn();
//            return targetPath;
//        }
//    }



    // 读取表中图片获取输出流
    public String readBin2Image(InputStream in) {
        String fileName = setFileName();
        String targetPath = saveFile(fileName).getPath();
        File file = new File(targetPath);
//        String path = targetPath.substring(0, targetPath.lastIndexOf("/"));
//        if (!file.exists()) {
//            new File(path).mkdir();
//        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d("tag-img-localpath", targetPath);
            return targetPath;
        }
    }

    public File saveFile(String filename) {
        File file = null;
        String storageState = Environment.getExternalStorageState();// 获取sd卡的状态
        if (Environment.MEDIA_MOUNTED.equals(storageState)) {// 如果已挂载状态
            //存储在SD卡文件夹image下
            File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/gznews_image");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            file = new File(Environment.getExternalStorageDirectory().getPath() + "/gznews_image/" + filename + ".jpg");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //存储在本地文件夹image下
            File dir = new File("/fsh_image");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file = new File("/fsh_image/" + filename + ".jpg");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    String setFileName() {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//获取当前时间，进一步转化为字符串
        date = new Date();
        String fileName = format.format(date);
        return fileName;
    }







}