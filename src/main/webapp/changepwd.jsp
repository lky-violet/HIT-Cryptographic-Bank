<%--
  Created by IntelliJ IDEA.
  User: paerxiusi
  Date: 2021/11/28
  Time: 10:23
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
                oldpwd=$("#oldpwd").val();
                newpwd=$("#newpwd").val();
                dualpwd=$("#dualpwd").val();
                if(newpwd!==dualpwd)
                {
                    alert("两次密码不一致，请确认密码！");
                    return false;
                }
                $.ajax({
                    url:'changepwd',//发送请求的地址
                    type:'POST',  //请求方式，默认为GET
                    dataType:"json",//返回值类型
                    data:{
                        "oldpwd":oldpwd, //用户名。
                        "pwd":newpwd,//用私钥加密的密码
                    },
                    success: (data)=>{
                        // alert(data);
                        alert(data.flag);
                        if(data.flag)
                        {
                            alert("修改密码成功！");
                        }
                        else{
                            alert("修改失败！");
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
                <i class="fa fa-key" aria-hidden="true"></i>
                <div>请输入旧密码</div>
                <input type="text" placeholder="oldpassword" name="oldpwd" id="oldpwd" required="required">
            </div>
            <div class="item">
                <i class="fa fa-key" aria-hidden="true"></i>
                <div>请输入新密码</div>
                <input type="password" placeholder="newpassword" name="newpwd" id="newpwd" required="required">
            </div>
            <div class="item">
                <i class="fa fa-key" aria-hidden="true"></i>
                <div>请确认新密码</div>
                <input type="password" placeholder="confirmpassword" name="dualpwd" id="dualpwd" required="required">
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
