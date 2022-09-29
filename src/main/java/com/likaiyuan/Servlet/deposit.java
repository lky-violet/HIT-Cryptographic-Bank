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

//存款服务
@WebServlet("/deposit")
public class deposit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //先得到当前的用户名
        String name=(String)req.getSession().getAttribute("personname");
        //得到当前的money数
        Daoimply2 jdbc=new Daoimply2();
        List<BankUser> userList=new ArrayList<>();
        userList=jdbc.findByusername(name);
        int money=userList.get(0).getMoney();
        String mycard=userList.get(0).getCardID();
        LocalDate datetime=LocalDate.now();
        String date=datetime.toString().replace("-","/");
        logMessage message=new logMessage();
        //得到前端传递的增加量,并用aes解密
//        String privkey=(String)req.getSession().getAttribute("privkey");
//        System.out.println("后端得到的私钥为：");
//        System.out.println(privkey);
        String decadd=req.getParameter("money");
//        String decadd= AESUtils.AESDecrypt(privkey,add);
        System.out.println("得到解密后的钱数为：");
        System.out.println(decadd);
        int addition=Integer.parseInt(decadd);
        //将存钱交易写入log
        message.setUsername(name);
        message.setDate(date);
        message.setMyID(mycard);
        message.setOtherID(mycard);
        message.setLog("save"+" "+addition+" "+"in the card");
        Daoimply3 jdbc2=new Daoimply3();
        boolean flag2=jdbc2.save(message);
        //得到新的存款数并更新
        int result=addition+money;
        boolean flag1=jdbc.updatemoney(name,result);
        req.getSession().setAttribute("money",result);
        Map<String,Object> map=new HashMap<>();
        ObjectMapper mapper=new ObjectMapper();
        if(flag1&&flag2)
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
}
