<%@ page language="java" contentType="text/html;" pageEncoding="gb2312"%>
<%
    java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    java.util.Date currentTime = new java.util.Date();
    String time = simpleDateFormat.format(currentTime).toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>
    <link href="css/admin.css" type="text/css" rel="stylesheet">
</head>
<body>
<table width="100%" cellpadding="0" cellspacing="0" align="center" background="0">
    <tr height="28">
        <td style="font-size: 14px" background="./image/title_bg1.jpg">&nbsp;&nbsp;µ±Ç°Î»ÖÃ£º</td>
    </tr>
    <tr>
        <td bgcolor="#b1ceef" height="2"></td>
    </tr>
    <tr height=20>
        <td background=./image/shadow_bg.jpg></td>
    </tr>
</table>
<table width="90%" cellpadding="0" cellspacing="0" border="0">
    <tr>
        <td height="150" colspan="4"></td>
    </tr>
    <tr height="200">
        <td height="200" width="80"></td>
        <td height="200" width="140">
            <img height="200" width="140" src="./image/admin_p.gif">
        </td>
        <td width="150"></td>
        <td>
            <table height="200" width="100%" cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td style="FONT-WEIGHT: bold; FONT-SIZE: 16px; color: red">
                        ÄúµÇÂ¼Ê±¼ä£º  <%=time %>
                    </td>
                </tr>
                <tr>
                    <td></td>
                </tr>
                <tr>
                    <td style="FONT-WEIGHT: bold; FONT-SIZE: 16px; color: red">
                        »¶Ó­½øÈëÍøÉÏÒøÐÐ½»Ò×ÖÐÐÄ£¡
                    </td>
                </tr>
            </table>
        </td>
    <tr>
        <td height="10" colspan="4"></td>
    </tr>
</table>
</body>
</html>