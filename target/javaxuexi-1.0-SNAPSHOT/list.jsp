<%@ page import="java.util.List" %>
<%@ page import="com.likaiyuan.pojo.logMessage" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: paerxiusi
  Date: 2021/12/10
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="jquery-3.6.0.min.js"></script>
</head>
<body>
<table id="tab1" border="1px" width="700px" align="center">
    <tr>
        <td colspan="6" align="center">
            <h1>交易记录</h1>
        </td>
    </tr>
    <tr align="center">
        <td>编号</td>
        <td>用户名</td>
        <td>交易时间</td>
        <td>交易信息</td>
        <td>自己卡号</td>
        <td>对方卡号</td>
    </tr>
    <c:forEach items="${requestScope.logs}" var="logmessage" varStatus="s">
        <tr align="center">
            <td>${s.count}</td>
            <td>${logmessage.username}</td>
            <td>${logmessage.date}</td>
            <td>${logmessage.log}</td>
            <td>${logmessage.myID}</td>
            <td>${logmessage.otherID}</td>
        </tr>
    </c:forEach>
</table>
<%--<c:if test="true">gunba</c:if>>--%>
<%--${3>4}--%>
</body>
</html>
