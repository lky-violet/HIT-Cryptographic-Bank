package com.likaiyuan.Dao;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.likaiyuan.jdbcutils.JDBCUtils;
import com.likaiyuan.pojo.BankUser;
import com.likaiyuan.pojo.User;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
//存储用户名密码表的操作
public class Daoimply1 {
    public List<User> findByusername(String name) {
        Connection conn=null;
        PreparedStatement pstmt=null;
        User us=null;
        List<User> result=new ArrayList<>();
        try {
            conn=JDBCUtils.getConnection();
            String sql="SELECT * from user where username=?";
            pstmt= conn.prepareStatement(sql);
            pstmt.setString(1,name);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next())
            {
                String username=rs.getString("username");
                String password=rs.getString("password");
                System.out.println("查询得到的用户名为：");
                System.out.println(username);
                System.out.println("查询得到密码为：");
                us=new User();
                us.setUsername(username);
                us.setPassword(password);
                result.add(us);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.close(pstmt,conn);
        }
        return result;
    }

    public boolean save(User user){
        String username= user.getUsername();
        String password=user.getPassword();
        Connection conn=null;
        PreparedStatement pstmt=null;
        int count=0;
        try {
            conn= JDBCUtils.getConnection();
            String sql="insert into user values(?,?)";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            count=pstmt.executeUpdate();
            System.out.println(count);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.close(pstmt,conn);

        }
        if(count==0)
        {
            return false;
        }
        else{
            return true;
        }
    }
    public boolean updatepwd(String name,String newpwd)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result=3;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "UPDATE user SET password=? where username=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newpwd);
            pstmt.setString(2,name);
            result= pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtils.close(pstmt, conn);
        }
        if(result==0) {
            return false;
        }
        else{
            return true;
        }
    }

}
