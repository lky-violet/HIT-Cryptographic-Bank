<%@ page import="java.util.*" language="java" contentType="text/html;" pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link href="css/admin.css" type="text/css" rel="stylesheet">
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" width="100%" background="./image/header_bg.jpg">
    <tr height="56">
        <td width="260"><img height="56" src="./image/header_left.jpg"></td>
        <td style="font-weight: bold; font-size: 14px; color: #FFF; padding-top: 20px;" align="center">
            当前用户：<span id="username"></span>

            <span id="now"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span><a id="menuChild" style="font-size: 13px; color:white; font-weight: bold;" href="/logout" target="_top">注销</a></span>
        </td>
        <td align="right" width="268">
            <img height="56" width="268" src="./image/header_right.jpg">
        </td>
    </tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr height="4" bgcolor="#1c5db6">
        <td></td>
    </tr>
</table>
<script type="text/javascript">
    function showTime(){
        //使用对象.innerText修改span元素间的内容
        document.getElementById("now").innerText="当前时间："+new Date().toLocaleString();
    }
    setInterval("showTime()",1000);//每隔1000毫秒(1s)调用showTime函数
    var username='<%=request.getSession().getAttribute("personname")%>';
    document.getElementById("username").innerText=username.toString();
    console.log(username)
</script>
</body>
</html>
