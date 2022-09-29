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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/trans")
public class transfer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入了trasfer的dopost方法");
        //得到日期时间
        String name=(String)req.getSession().getAttribute("personname");
        System.out.println("可以得到名字");
        String hisID=req.getParameter("cardID");//得到对方的卡号
        String money=req.getParameter("money");
        String date=req.getParameter("date");
        int variable=Integer.parseInt(money);
        //首先得到两个账户的钱数
        Daoimply2 jdbc1=new Daoimply2();
        List<BankUser> user=new ArrayList<>();
        user=jdbc1.findByusername(name);
        String myID=user.get(0).getCardID();
        int my=user.get(0).getMoney();
        /*
        测试
         */
        System.out.println("得到双方的卡号为");
        System.out.println(myID);
        System.out.println(hisID);
        List<BankUser> list=new ArrayList<>();
        list=jdbc1.findByID(hisID);
        System.out.println(list);
        int his= list.get(0).getMoney();
        System.out.println("得到的双方钱数分别为");
        System.out.println(my);
        System.out.println(his);
        //然后比较自己的钱数与要转账的钱数,得到两个账户现在的钱数
        Map<String,Object> map=new HashMap<>();
        ObjectMapper mapper=new ObjectMapper();
        if(my<variable)
        {
            map.put("flag",false);
            return;
        }
        int nowmy=my-variable;
        int nowhis=his+variable;
        System.out.println("即将要更新的钱数");
        System.out.println(nowhis);
        System.out.println(nowmy);
        //更新钱数
        boolean flag1;
        flag1=jdbc1.updatebyID(myID,nowmy);
        flag1=jdbc1.updatebyID(hisID,nowhis);
        //最后还需将这笔交易写入log
        //先写自己的log
        Daoimply3 jdbc2=new Daoimply3();
        logMessage mylog=new logMessage();
        mylog.setUsername(name);
        mylog.setDate(date);
        mylog.setLog("send "+variable+" to "+hisID);
        mylog.setMyID(myID);
        mylog.setOtherID(hisID);
        boolean flag2=jdbc2.save(mylog);
        //再写对方的log
        logMessage hislog=new logMessage();
        hislog.setUsername(list.get(0).getUsername());
        hislog.setDate(date);
        hislog.setLog("receive "+variable+" from "+myID);
        hislog.setMyID(hisID);
        hislog.setOtherID(myID);
        boolean flag3=jdbc2.save(hislog);
        if(flag1&&flag2&&flag3)
        {
            req.getSession().setAttribute("money",nowmy);
            map.put("flag",true);
            mapper.writeValue(resp.getWriter(),map);
        }
        else{
            map.put("flag",false);
            mapper.writeValue(resp.getWriter(),map);
        }
    }
}
