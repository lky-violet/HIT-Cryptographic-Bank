package com.likaiyuan.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likaiyuan.Dao.Daoimply2;
import com.likaiyuan.Dao.Daoimply3;
import com.likaiyuan.pojo.BankUser;
import com.likaiyuan.pojo.logMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.*;

@WebServlet("/payentry")
public class PayEntry extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    /*
     *前端的界面发送至该方法，进行数据库操作
     * parameters:
     * username:xxx
     * ID:xxxx
     * money:xxx
     * ordernumber:xxx
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入payentry的post方法");
        String myname=req.getParameter("username");
        String money=req.getParameter("money");
        //date用来写自己的记录
        LocalDate datetime=LocalDate.now();
        String date=datetime.toString().replace("-","/");
        int changemoney=Integer.parseInt(money);
        String ordernumber=req.getParameter("ordernumber");
        String hisID=req.getParameter("ID");
        //时间戳
        Long time=new Date().getTime();
        //得到银行客户的银行卡号,当前账号的钱数
        System.out.println("得到了前端传递的基础信息");
        System.out.println(money);
        System.out.println(myname);
        System.out.println(ordernumber);
        System.out.println(hisID);
        /*
         *
         */
        Daoimply2 jdbc=new Daoimply2();
        List<BankUser> list=new ArrayList<>();
        list=jdbc.findByusername(myname);
        String myID=list.get(0).getCardID();
        int mymoney=list.get(0).getMoney();
        //进行转账操作
        //得到对方的钱数，用户名
        List<BankUser> list2=jdbc.findByID(hisID);
        int hismoney=list2.get(0).getMoney();
        String hisname=list2.get(0).getUsername();
        //得到双方现在的钱数
        int nowmy=mymoney-changemoney;
        int nowhis=hismoney+changemoney;
        boolean flag1;
        //进行更新数据库的操作
        System.out.println("现在要更新的客户卡号,钱数为");
        System.out.println(myID);
        System.out.println(nowmy);
        flag1=jdbc.updatebyID(myID,nowmy);
        flag1=jdbc.updatebyID(hisID,nowhis);
        //更新双方的日志信息
        Daoimply3 jdbc2=new Daoimply3();
        logMessage mylog=new logMessage();
        mylog.setUsername(myname);
        mylog.setDate(date);
        mylog.setLog("send "+money+" to "+hisID);
        mylog.setMyID(myID);
        mylog.setOtherID(hisID);
        boolean flag2=jdbc2.save(mylog);
        //再写对方的log
        logMessage hislog=new logMessage();
        hislog.setUsername(hisname);
        hislog.setDate(date);
        hislog.setLog("receive "+money+" from "+myID);
        hislog.setMyID(hisID);
        hislog.setOtherID(myID);
        boolean flag3=jdbc2.save(hislog);
        Map<String,Object> map=new HashMap<>();
        ObjectMapper mapper=new ObjectMapper();
        if(flag1&&flag2&&flag3)
        {
//            req.setAttribute("myID",myID);
//            req.setAttribute("transmoney",money);
//            req.setAttribute("order",ordernumber);
//            req.setAttribute("date",time);
//            req.setAttribute("PI","yes");
            System.out.println("成功转发");
            //由success界面负责向淘宝发送返回数据
//            map.put("myID",myID);
//            map.put("transmoney",money);
//            map.put("ordernumber",ordernumber);
//            map.put("transdate",time);
            map.put("flag",true);
            req.getSession().setAttribute("transdate",time);
            req.getSession().setAttribute("orderNUM",ordernumber);
            System.out.println("我跳了哈哈哈哈");
//            req.getRequestDispatcher(req.getContextPath()+"/success.jsp").forward(req,resp);
            mapper.writeValue(resp.getWriter(),map);
        }
        else{
            req.setAttribute("ordernumber",ordernumber);
            req.setAttribute("date",time);
            req.setAttribute("PI","no");
            System.out.println("成功转发");
            req.getRequestDispatcher("error.jsp").forward(req,resp);
        }

    }
}
