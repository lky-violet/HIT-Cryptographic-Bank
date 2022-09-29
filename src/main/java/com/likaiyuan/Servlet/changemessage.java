package com.likaiyuan.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likaiyuan.Dao.Daoimply2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/changemessage")
public class changemessage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //首先获得所有传递的参数(认为只能修改四个）
        String name=(String)req.getSession().getAttribute("personname");
        String age=req.getParameter("age");
        String email=req.getParameter("email");
        String telephone=req.getParameter("telephone");
        String address=req.getParameter("address");
        //然后依次将其更新
        Daoimply2 jdbc=new Daoimply2();
        boolean flag=jdbc.updatemessage(name,age,address,telephone,email);
        Map<String,Object> map=new HashMap<>();
        ObjectMapper mapper=new ObjectMapper();
        if(flag)
        {
            System.out.println("我跳了");
            map.put("flag",true);
            mapper.writeValue(resp.getWriter(),map);
        }
        else{
            System.out.println("我没跳");
            map.put("flag",false);
            mapper.writeValue(resp.getWriter(),map);
        }
    }
}
