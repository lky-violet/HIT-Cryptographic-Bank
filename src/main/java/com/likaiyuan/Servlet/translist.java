package com.likaiyuan.Servlet;

import com.likaiyuan.Dao.Daoimply3;
import com.likaiyuan.pojo.logMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/translist")
public class translist extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入list的dopost方法");
        //查询该用户的所有交易记录
        String name=(String)req.getSession().getAttribute("personname");
        //得到交易记录的list
        List<logMessage> list=new ArrayList<>();
        Daoimply3 jdbc=new Daoimply3();
        list=jdbc.findByusername(name);
        //将list存入request区域，request.setattribute("users",list)
        System.out.println("成功设置了list");
        req.setAttribute("logs",list);
        //转发到list.jsp:request.getRequestDispatcher("list.jsp).forword(,)
        System.out.println("成功转发");
        req.getRequestDispatcher("list.jsp").forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入list的doget方法");
        doPost(req,resp);
    }
}
