<%--
  Created by IntelliJ IDEA.
  User: paerxiusi
  Date: 2021/11/28
  Time: 10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <link rel="stylesheet" href="css/newpage.css">
    <link rel="stylesheet" href="css/font-awesome.css">
    <script type="text/javascript" src="jquery-3.6.0.min.js"></script>
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
    </style>
    <script type="text/javascript">

        $(function(){
            $("#loginbtn").click(function(){
                cardID=$("#cardID").val();
                money=$("#money").val();
                date=new Date();
                datetime=date.toLocaleDateString();
                $.ajax({
                    url:'trans',//发送请求的地址
                    type:'POST',  //请求方式，默认为GET
                    dataType:"json",//返回值类型
                    data:{
                        "cardID":cardID, //用户名。
                        "money":money,//用私钥加密的密码
                        "date":datetime
                    },
                    success: (data)=>{
                        // alert(data);
                        alert(data.flag);
                        if(data.flag)
                        {
                            alert("转账成功！");
                        }
                        else{
                            alert("转账失败！");
                        }
                    }
                });
                alert("传输成功");
                return true;
            });
        })
    </script>
</head>

<body>
<form action="#" method="POST">
    <div id="login-box">
        <h1>Login</h1>
        <div class="form">
            <div class="item">
                <i class="fa fa-user-circle" aria-hidden="true"></i>
                <div>请输入对方卡号</div>
                <input type="text" placeholder="cardID" name="cardID" id="cardID" required="required">
            </div>
            <div class="item">
                <i class="fa fa-user-circle" aria-hidden="true"></i>
                <div>请输入转账金额</div>
                <input type="text" placeholder="money" name="money" id="money" required="required">
            </div>
        </div>
        <button type="button" id="loginbtn">提交</button>
    </div>
</form>
</body>

<footer>
    <div id="message-box">Hello,Welcome to Dream Bank</div>
</footer>

</html>
