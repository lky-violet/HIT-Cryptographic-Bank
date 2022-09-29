package com.likaiyuan.Servlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.likaiyuan.Crypyto.SHA256;
import com.likaiyuan.Dao.Daoimply1;
import com.likaiyuan.Dao.Daoimply2;
import com.likaiyuan.pojo.User;

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

@WebServlet("/changepwd")
public class changepwd extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入changepwd的post方法");
        //得到新旧密码
        String oldpwd=req.getParameter("oldpwd");
        String newpwd=req.getParameter("pwd");
        String compare= SHA256.sha256(oldpwd);
        String insert=SHA256.sha256(newpwd);
        //先对旧密码进行判断，如果正确，则同意修改，否则不同意进行修改
        Map<String,Object> map=new HashMap<>();
        ObjectMapper mapper=new ObjectMapper();
        //从数据库中查询密码
        String name=(String)req.getSession().getAttribute("personname");
        Daoimply1 jdbc=new Daoimply1();
        List<User> list=new ArrayList<>();
        list=jdbc.findByusername(name);
        String oldone=list.get(0).getPassword();
        //如果旧密码不一样拒绝修改
        if(!oldone.equals(compare)){
            map.put("flag",false);
            mapper.writeValue(resp.getWriter(),map);
            return;
        }
        //旧密码正确，进行修改
        boolean flag=jdbc.updatepwd(name,insert);
        if(flag) {
            map.put("flag", true);
            mapper.writeValue(resp.getWriter(), map);
        }
        else{
            map.put("flag",false);
            mapper.writeValue(resp.getWriter(),map);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
