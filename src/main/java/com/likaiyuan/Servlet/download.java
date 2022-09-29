package com.likaiyuan.Servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/download")
public class download extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ----将文件传给浏览器----
        String filename = "99529CD8ECB1FE54B39F8E9C85B349F5C8DD926C666C22B62378237D670BD018.cert";
        System.out.println("传给浏览器的文件名 = "+filename);
        // response: 准备setHeader: content-type
        System.out.println("response: 准备setHeader: content-type");
        resp.setHeader("content-type", "application/octet-stream");
        System.out.println("response: 完成setHeader: content-type");
        // response: 准备setHeader: content-disposition
        System.out.println("response: 准备setHeader: content-disposition");
        resp.setHeader("content-disposition", "attachment;filename=" + filename);
        System.out.println("response: 完成setHeader: content-disposition");
        // 创建FileInputStream
        System.out.println("创建FileInputStream");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("/Users/paerxiusi/Documents/javaxuexi/src/main/java/com/likaiyuan/file/99529CD8ECB1FE54B39F8E9C85B349F5C8DD926C666C22B62378237D670BD018.cert");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("完成创建FileInputStream");
        // 创建ServletOutputStream
        System.out.println("创建ServletOutputStream");
        ServletOutputStream servletOutputStream = resp.getOutputStream();
        System.out.println("完成创建ServletOutputStream");
        byte[] buff = new byte[5000 * 8];
        int len = 0;
        while ((len = fileInputStream.read(buff)) != -1) {
            servletOutputStream.write(buff, 0, len);
        }
        fileInputStream.close();
    }
}
