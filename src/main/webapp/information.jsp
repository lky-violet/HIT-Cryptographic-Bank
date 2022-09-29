<%--
  Created by IntelliJ IDEA.
  User: paerxiusi
  Date: 2021/11/28
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>个人信息详情</title>
</head>
<body>
<div align="center">
    <table width="450" class="table" cellpadding="0" cellspacing="0" border="0">
        <tbody>
        <tr>
            <td>账户　ID：</td>
            <td width="360" height="30">
                <input type="text" name="user.accountid" value='<%=request.getSession().getAttribute("cardID")%>' />
            </td>
        </tr>
        <tr>
            <td>姓　　名：</td>
            <td width="360" height="30">
                <input type="text" name="personinfo.realname" value='<%=request.getSession().getAttribute("personname")%>' />
            </td>
        </tr>
        <tr>
            <td>年　　龄：</td>
            <td width="360" height="30">
                <input type="text" name="personinfo.age" value='<%=request.getSession().getAttribute("age")%>' />
            </td>
        </tr>
        <tr>
            <td>家庭住址：</td>
            <td width="360" height="30">
                <input type="text" name="personinfo.address" value='<%=request.getSession().getAttribute("address")%>' />
            </td>
        </tr>
        <tr>
            <td>联系电话：</td>
            <td width="360" height="30">
                <input type="text" name="personinfo.telephone" value='<%=request.getSession().getAttribute("telephone")%>' />
            </td>
        </tr>
        <tr>
            <td>邮箱：</td>
            <td width="360" height="30">
                <input type="text" name="personinfo.cardid" value='<%=request.getSession().getAttribute("email")%>' />
            </td>
        </tr>
        <tr>
            <td>账户余额：</td>
            <td width="360" height="30">
                <input type="text" name="user.balance" value='<%=request.getSession().getAttribute("money")%>' />
            </td>
        </tr>
        </tbody>
    </table>

</div>
</body>
</html>