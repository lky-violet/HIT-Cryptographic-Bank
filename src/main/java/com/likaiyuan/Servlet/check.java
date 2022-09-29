package com.likaiyuan.Servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/check")
public class check extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int width=100;
        int height=50;
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g= image.getGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0,0,width,height);
        g.setColor(Color.PINK);
        g.drawRect(0,0,width-1,height-1);
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        Random ran=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=1;i<=4;i++)
        {
            int index=ran.nextInt(str.length());
            char ch=str.charAt(index);
            sb.append(ch);
            g.drawString(ch+"",(width/5)*i,25);
        }
        String checkcode=sb.toString();
        System.out.println("成功设置了验证码");
        req.getSession().setAttribute("checkcode",checkcode);
        g.setColor(Color.GREEN);
        //随机划线
        for(int i=0;i<5;i++)
        {
            int x1=ran.nextInt(width);
            int y1=ran.nextInt(height);
            int x2=ran.nextInt(width);
            int y2=ran.nextInt(height);
            g.drawLine(x1,y1,x2,y2);

        }
        ImageIO.write(image,"jpg",resp.getOutputStream());
    }
}
