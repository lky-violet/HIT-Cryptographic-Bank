package com.likaiyuan.Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/logout")
public class logout extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Enumeration em=req.getSession().getAttributeNames();
        while (em.hasMoreElements()){
            req.getSession().removeAttribute(em.nextElement().toString());
        }
        System.out.println("成功清除session");
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }
}
