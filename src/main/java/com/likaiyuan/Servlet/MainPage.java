package com.likaiyuan.Servlet;

import com.likaiyuan.Dao.Daoimply2;
import com.likaiyuan.pojo.BankUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/mainpage")
public class MainPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name=(String)req.getSession().getAttribute("personname");
        System.out.println("Mainpage中的name为：");
        System.out.println(name);
        //在Mainpage中实现个性化服务
        Daoimply2 jdbc=new Daoimply2();
        List<BankUser> user=new ArrayList<>();
        user=jdbc.findByusername(name);
        System.out.println("查询得到的用户信息为：");
        System.out.println(user);
        req.getSession().setAttribute("age",user.get(0).getAge());
        req.getSession().setAttribute("email",user.get(0).getEmail());
        req.getSession().setAttribute("address",user.get(0).getAddress());
        req.getSession().setAttribute("telephone",user.get(0).getTelephone());
        req.getSession().setAttribute("cardID",user.get(0).getCardID());
        req.getSession().setAttribute("money",user.get(0).getMoney());
        req.getRequestDispatcher("main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
