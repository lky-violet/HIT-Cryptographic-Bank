package com.likaiyuan.Dao;



import com.likaiyuan.jdbcutils.JDBCUtils;
import com.likaiyuan.pojo.BankUser;
import com.likaiyuan.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//对bankuser表的操作
public class Daoimply2{
    public List<BankUser> findByusername(String name) {
        Connection conn=null;
        PreparedStatement pstmt=null;
        BankUser us=null;
        List<BankUser> result=new ArrayList<>();
        try {
            conn= JDBCUtils.getConnection();
            String sql="SELECT * from bankuser where username=?";
            pstmt= conn.prepareStatement(sql);
            pstmt.setString(1,name);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next())
            {
                String username=rs.getString("username");
                String age=rs.getString("age");
                String address=rs.getString("address");
                String email=rs.getString("email");
                String telephone=rs.getString("telephone");
                String cardID=rs.getString("cardID");
                int money=rs.getInt("money");
                System.out.println("查询得到的用户名为：");
                System.out.println(username);
                us=new BankUser();
                us.setUsername(username);
                us.setCardID(cardID);
                us.setMoney(money);
                us.setEmail(email);
                us.setAge(age);
                us.setAddress(address);
                us.setTelephone(telephone);
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
    public boolean save(BankUser user) {
        String username= user.getUsername();
        String age=user.getAge();
        String email=user.getEmail();
        String telephone=user.getTelephone();
        String address=user.getAddress();
        String cardID=user.getCardID();
        int money=user.getMoney();
        Connection conn=null;
        PreparedStatement pstmt=null;
        int count=0;
        try {
            conn= JDBCUtils.getConnection();
            String sql="insert into bankuser values(?,?,?,?,?,?,?)";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,age);
            pstmt.setString(3,address);
            pstmt.setString(4,email);
            pstmt.setString(5,telephone);
            pstmt.setString(6,cardID);
            pstmt.setInt(7,money);
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
    public boolean updatemoney(String name,int money)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result=3;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "UPDATE bankuser SET money=? where username=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, money);
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
    public boolean updatemessage(String name,String age,String address,String tele,String email)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result=3;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "UPDATE bankuser SET age=? where username=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, age);
            pstmt.setString(2,name);
            result= pstmt.executeUpdate();
            sql = "UPDATE bankuser SET address=? where username=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, address);
            pstmt.setString(2,name);
            result= pstmt.executeUpdate();
            sql = "UPDATE bankuser SET telephone=? where username=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tele);
            pstmt.setString(2,name);
            result= pstmt.executeUpdate();
            sql = "UPDATE bankuser SET email=? where username=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
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
    public boolean updatebyID(String ID,int money)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result=3;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "UPDATE bankuser SET money=? where cardID=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2,ID);
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
    public List<BankUser> findByID(String ID) {
        Connection conn=null;
        PreparedStatement pstmt=null;
        BankUser us=null;
        List<BankUser> result=new ArrayList<>();
        try {
            conn= JDBCUtils.getConnection();
            String sql="SELECT * from bankuser where cardID=?";
            pstmt= conn.prepareStatement(sql);
            pstmt.setString(1,ID);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next())
            {
                String username=rs.getString("username");
                String age=rs.getString("age");
                String address=rs.getString("address");
                String email=rs.getString("email");
                String telephone=rs.getString("telephone");
                String cardID=rs.getString("cardID");
                int money=rs.getInt("money");
                System.out.println("查询得到的用户名为：");
                System.out.println(username);
                us=new BankUser();
                us.setUsername(username);
                us.setCardID(cardID);
                us.setMoney(money);
                us.setEmail(email);
                us.setAge(age);
                us.setAddress(address);
                us.setTelephone(telephone);
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


}
