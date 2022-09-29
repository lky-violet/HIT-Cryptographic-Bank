<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>
    <link href="css/admin.css" type="text/css" rel="stylesheet" >
</head>
<body>
<table width="170" height="100%" cellspacing="0" cellpadding="0" background="./image/1e10d7fd8281eb2f4a66eb5a93aa90a3.jpeg" border="0">
    <tr>
        <td valign="top" align="center">
            <table width="150" cellpadding="0" cellspacing="0" border="0">
                <tr height="25">
                    <td style="padding-left: 30px" background="./image/menu_bt.jpg">
                        <a style="font-size: 15px" class="menuParent" onclick="expend(1)" href="javascript:void(0);">操作菜单</a>
                    </td>
                </tr>
                <tr height="6">
                    <td></td>
                </tr>
            </table>
            <table id="child0" width="150" cellpadding="0" cellspacing="0" border="0">
                <tr height="40">
                    <td width="30" align="center">
                        <img width="9" height="9" src="./image/444ae30371c677f12d5a9b3feccf9b40.jpeg">
                    </td>
                    <td>
                        <a class="menuChild" style="font-size: 13px" href="deposit.jsp" target="main">存款</a>
                    </td>
                </tr>
                <tr height="40">
                    <td width="30" align="center">
                        <img width="9" height="9" src="./image/444ae30371c677f12d5a9b3feccf9b40.jpeg">
                    </td>
                    <td>
                        <a class="menuChild" style="font-size: 13px" href="withdraw.jsp" target="main">取款</a>
                    </td>
                </tr>
                <tr height="40">
                    <td width="30" align="center">
                        <img width="9" height="9" src="./image/444ae30371c677f12d5a9b3feccf9b40.jpeg">
                    </td>
                    <td>
                        <a class="menuChild" style="font-size: 13px" href="transfer.jsp" target="main">转账</a>
                    </td>
                </tr>
                <tr height="40">
                    <td width="30" align="center">
                        <img width="9" height="9" src="./image/444ae30371c677f12d5a9b3feccf9b40.jpeg">
                    </td>
                    <td>
                        <a class="menuChild" style="font-size: 13px" href="translist" target="main">查询交易记录</a>
                    </td>
                </tr>
                <tr height="40">
                    <td width="30" align="center">
                        <img width="9" height="9" src="./image/444ae30371c677f12d5a9b3feccf9b40.jpeg">
                    </td>
                    <td>
                        <a class="menuChild" style="font-size: 13px" href="information" target="main">查看个人信息</a>
                    </td>
                </tr>
                <tr height="40">
                    <td width="30" align="center">
                        <img width="9" height="9" src="./image/444ae30371c677f12d5a9b3feccf9b40.jpeg">
                    </td>
                    <td>
                        <a class="menuChild" style="font-size: 13px" href="changemessage.jsp" target="main">修改个人信息</a>
                    </td>
                </tr>
                <tr height="40">
                    <td width="30" align="center">
                        <img width="9" height="9" src="./image/444ae30371c677f12d5a9b3feccf9b40.jpeg">
                    </td>
                    <td>
                        <a class="menuChild" style="font-size: 13px" href="changepwd.jsp" target="main">修改密码</a>
                    </td>
                </tr>
                <tr height="40">
                    <td width="30" align="center">
                        <img width="9" height="9" src="./image/444ae30371c677f12d5a9b3feccf9b40.jpeg">
                    </td>
                    <td>
                        <a class="menuChild" style="font-size: 13px" href="logout" target="_top">注销</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
