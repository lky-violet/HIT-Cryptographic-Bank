package com.likaiyuan.Dao;

import com.likaiyuan.jdbcutils.JDBCUtils;
import com.likaiyuan.pojo.BankUser;
import com.likaiyuan.pojo.User;
import com.likaiyuan.pojo.logMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//对logtable表进行操作
public class Daoimply3 {
    public boolean save(logMessage message) {
        String username= message.getUsername();
        String date=message.getDate();
        String log=message.getLog();
        String myID=message.getMyID();
        String hisID=message.getOtherID();
        Connection conn=null;
        PreparedStatement pstmt=null;
        int count=0;
        try {
            conn= JDBCUtils.getConnection();
            String sql="insert into logtable values(?,?,?,?,?)";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,date);
            pstmt.setString(3,log);
            pstmt.setString(4,myID);
            pstmt.setString(5,hisID);
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
    public List<logMessage> findByusername(String name) {
        Connection conn=null;
        PreparedStatement pstmt=null;
        logMessage message;
        List<logMessage> result=new ArrayList<>();
        try {
            conn=JDBCUtils.getConnection();
            String sql="SELECT * from logtable where username=?";
            pstmt= conn.prepareStatement(sql);
            pstmt.setString(1,name);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next())
            {
                String username=rs.getString("username");
                String date=rs.getString("date");
                String log=rs.getString("log");
                String myId=rs.getString("myID");
                String otherID=rs.getString("otherID");
                System.out.println("查询得到的用户名为：");
                System.out.println(username);
                message=new logMessage();
                message.setUsername(username);
                message.setDate(date);
                message.setLog(log);
                message.setMyID(myId);
                message.setOtherID(otherID);
                result.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.close(pstmt,conn);
        }
        return result;
    }
}
