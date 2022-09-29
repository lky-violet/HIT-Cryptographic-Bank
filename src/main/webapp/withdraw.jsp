<%--
  Created by IntelliJ IDEA.
  User: paerxiusi
  Date: 2021/11/28
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript" src="./labjs/jsbn.js"></script>
    <script type="text/javascript" src="./labjs/RSAUtils.js"></script>
    <script type="text/javascript" src="jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="./labjs/aes.js"></script>
    <script type="text/javascript" src="./labjs/aeslib.js"></script>
    <link rel="stylesheet" href="css/newpage.css">
    <link rel="stylesheet" href="css/font-awesome.css">
    <style>
        input::-webkit-input-placeholder {
            color:white;
        }
        input::-moz-placeholder {
            /* Mozilla Firefox 19+ */
            color: white;
        }
        input:-moz-placeholder {
            /* Mozilla Firefox 4 to 18 */
            color: white;
        }
        input:-ms-input-placeholder {
            /* Internet Explorer 10-11 */
            color: white;
        }
        span{
            color: white;
        }
    </style>
    <title>取款</title>
</head>
<body>
<%--<form action="#" name="myform" method="post" onsubmit="return deposit()">--%>
<%--    <div align="center">--%>
<%--        <table>--%>
<%--            <tbody>--%>
<%--            <tr>--%>
<%--                <td>取款时间：</td>--%>
<%--                <td width="360" height="30">--%>
<%--                    <span id="now"></span>--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>取款金额：</td>--%>
<%--                <td width="360" height="30">--%>
<%--                    <input type="text" name="money" id="money" />--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td></td>--%>
<%--                <td width="360" height="30">--%>
<%--                    <input type="button" value="取款" id="confirmbutton" />--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--            </tbody>--%>
<%--        </table>--%>
<%--    </div>--%>
<%--</form>--%>
<form action="#" method="POST">
    <div id="login-box">
        <h1>取款</h1>
        <div class="form">
            <div class="item">
                <i class="fa fa-user-circle" aria-hidden="true"></i>
                <span id="now"></span>
            </div>

            <div class="item">
                <i class="fa fa-key" aria-hidden="true"></i>
                <input type="text" name="money" id="money" />
            </div>
        </div>
        <input type="button" value="取款" id="confirmbutton" />
    </div>
</form>
<script type="text/javascript">
    function showTime(){
        //使用对象.innerText修改span元素间的内容
        document.getElementById("now").innerText=new Date().toLocaleString();
    }
    setInterval("showTime()",1000);//每隔1000毫秒(1s)调用showTime函数
</script>
</body>
<footer>
    <div id="message-box">Hello,Welcome to Dream Bank</div>
</footer>
<script type="text/javascript">
    //得到私钥
    <%--var privkey='<%=request.getSession().getAttribute("privkey")%>';--%>
    <%--console.log(privkey);--%>
    var result;
    $(function() {
        $("#confirmbutton").click(function () {
            //判断输入是否合法
            var money = document.getElementById("money").value;
            //alert(money.length);
            if(money.length>0) {
                if(!(money.search(/^[\+\-]?\d+\.?\d*$/)===0)) {
                    alert("含有非法字符!");
                    return false;
                }else {
                    if(parseFloat(money)===0) {
                        alert("金额必须大于0!");
                        return false;
                    }
                }
            } else {
                alert("金额不能为空！");
                return false;
            }
            //用私钥加密密码
            result = money;
            $.ajax({
                url: 'withdraw',//发送请求的地址
                type: 'POST',  //请求方式，默认为GET
                dataType: "json",//返回值类型
                data: {
                    "money":result
                },
                success: (data) => {
                    // alert(data);
                    alert(data.flag);
                    if (data.flag) {
                        alert("取款成功！");
                    } else {
                        alert("取款失败！！不可透支取款！");
                    }
                }
            });
            alert("传输成功");
            return true;
        });
    })
</script>
</html>
