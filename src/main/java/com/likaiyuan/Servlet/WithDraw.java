package com.likaiyuan.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likaiyuan.Crypyto.AESUtils;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/withdraw")
public class WithDraw extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //先得到当前的用户名
        String name=(String)req.getSession().getAttribute("personname");
        //得到当前的money数
        Daoimply2 jdbc=new Daoimply2();
        List<BankUser> userList=new ArrayList<>();
        userList=jdbc.findByusername(name);
        int money=userList.get(0).getMoney();
        String myname=userList.get(0).getUsername();
        String cardID=userList.get(0).getCardID();
        LocalDate datetime=LocalDate.now();
        String date=datetime.toString().replace("-","/");
        //得到前端传递的增加量,并用aes解密
//        String privkey=(String)req.getSession().getAttribute("privkey");
//        System.out.println("后端得到的私钥为：");
//        System.out.println(privkey);
        String add=req.getParameter("money");
        String decadd= add;
        System.out.println("得到解密后的钱数为：");
        System.out.println(decadd);
        int addition=Integer.parseInt(decadd);
        //写入log
        logMessage message=new logMessage();
        message.setDate(date);
        message.setUsername(name);
        message.setMyID(cardID);
        message.setOtherID(cardID);
        message.setLog("Withdraw"+" "+addition+" from card.");
        Map<String,Object> map=new HashMap<>();
        ObjectMapper mapper=new ObjectMapper();
        //得到新的取款数并更新
        int result=0;
        if(addition>money)
        {
            System.out.println("我没跳");
            map.put("flag",false);
            mapper.writeValueAsString(map);
            mapper.writeValue(resp.getWriter(),map);
            return;
        }
        result=money-addition;
        boolean flag=jdbc.updatemoney(name,result);
        Daoimply3 jdbc2=new Daoimply3();
        boolean flag2=jdbc2.save(message);
        req.getSession().setAttribute("money",result);
        if(flag&&flag2)
        {
            System.out.println("我跳了");
            map.put("flag",true);
            mapper.writeValue(resp.getWriter(),map);
        }
        else{
            System.out.println("我没跳");
            map.put("flag",false);
            mapper.writeValueAsString(map);
            mapper.writeValue(resp.getWriter(),map);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
