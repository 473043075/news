package com.example.a13797.gznews.method;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Administrator on 2018/12/9.
 */

public class DbConnection {

    private PreparedStatement ptmt;
    private Statement stmt;
    private ResultSet resultSet;
    Connection conn;


    public Connection getConnection(){

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String url = "jdbc:jtds:sqlserver://123.206.19.177:1433/gznews;charset=utf8";
            conn= DriverManager.getConnection(url, "sa", "!gz123456");
            if (conn!= null) {
                Log.d("tag-conn", "数据库连接成功！");
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
//            Log.d("tag-conn","驱动找不到！");
        }catch(SQLException e){
            e.printStackTrace();
//            Log.d("tag-conn","数据库连接失败！");
        }catch(Exception e){
            e.printStackTrace();
//            Log.d("tag-conn","连接数据源失败！");
        }
        return conn;
    }

    public void closeConn(){
        try {
            if (ptmt != null) {
                ptmt.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (conn != null) {
                conn.close();
            }
            if(stmt!=null){
                stmt.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
