<%--
  Created by IntelliJ IDEA.
  User: paerxiusi
  Date: 2021/10/17
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <script type="text/javascript" src="./labjs/jsbn.js"></script>
    <script type="text/javascript" src="./labjs/RSAUtils.js"></script>
    <script type="text/javascript" src="./labjs/aes.js"></script>
    <script type="text/javascript" src="./labjs/aeslib.js"></script>
    <script type="text/javascript" src="./labjs/sha_dev.js"></script>
    <script type="text/javascript" src="./labjs/banksignature.js"></script>
    <title>注册页面</title>
    <style>
        *{
            margin:0px;
            padding:0px;
            box-sizing:border-box;
        }
        body{
            background: url("./image/20499c3d0ac5cb5983c86063262b52da.jpeg");
            background-size: cover;
        }
        .rg_layout{
            width:700px;
            height:450px;
            border:3px solid #EEEEEE;
            background-color: rgba(255,255,255,0.6);
            margin:auto;
            margin-top: 80px;
            margin-left: 700px;
        }
        .rg_left{
            /*border:1px solid red;*/
            float:left
        }
        .rg_left > p:first-child{
            color:blue;
            font-size: 20px;
            margin: 15px;

        }
        .rg_left > p:last-child{
            color:gray;
            font-size: 20px;

        }
        .rg_center{
            /*border:1px solid red;*/
            float:left;
            width:400px
        }
        .rg_right{
            /*border:1px solid red;*/
            float:right
        }
        .rg_right > p:first-child{
            color:black;
            font-size: 15px;
            margin: 5px;
        }
        .rg_right> p:last-child{
            color:gray;
            font-size: 15px;
            margin:5px;
        }
        .td_left{
            width:100px;
            text-align:right;
            height:45px;
        }
        .td_right{
            padding-left: 50px;
        }
        #username,#password,#email,#name{
            width:100px;
            height:50px;
        }
        #btn_sub{
            width:150px;
            height:40px;
            background-color: yellow;
            border: 1px solid;
        }
    </style>
    <script src="jquery-3.6.0.min.js"></script>
    <script>
        // 获取N
        var publicModulusString = '<%=request.getSession().getAttribute("module")%>';
        //获取e
        var publicExponentString = '<%=request.getSession().getAttribute("exponent")%>';
        //将字符串转换为大整型
        // var publicModulus = new BigInteger(publicModulusString,16);
        var publicModulus = new BigInteger(publicModulusString);
        //将公钥转换为大整型
        // var publicExponent = new BigInteger(publicExponentString, 16);
        var publicExponent = new BigInteger(publicExponentString);
        //得到公钥
        var rsaPublicKey = new RSAPublicKey(publicExponent, publicModulus);
        //得到私钥
        var privatekey=AESGenerateKey(32);
        console.log(publicModulusString)
        console.log(publicExponentString)
        var pwd = null;
        //登陆函数
        $(function(){
            $("#registerForm").submit(function(){
                //先对输入进行判断
                pwd = $("#password").val();
                //判断密码是否为空
                if(pwd==0)
                {
                    alert("密码不能为空！")
                    return false;
                }
                //判断前后输入密码是否一致
                dualpwd=$("#finalpass").val();
                if(pwd!=dualpwd)
                {
                    alert("两次密码不一致，请确认密码！");
                    return false;
                }
                //判断年龄是否为数字
                age=$("#age").val();
                var reg=/^[0-9]+$/;
                if(!reg.test(age))/*定义验证表达式*/
                {
                    alert("年龄必须为数字！");
                    return false;
                }
                //判断电话号码是否为11位数字
                tele=$("#telephone").val();
                reg=/^[0-9]{11}$/;
                if(!reg.test(tele))/*定义验证表达式*/
                {
                    alert("请输入正确的11位电话号码！");
                    return false;
                }
                var publicModulus = new BigInteger(publicModulusString);
                var publicExponent = new BigInteger(publicExponentString);
                var rsaPublicKey = new RSAPublicKey(publicExponent, publicModulus);
                var set=EncryptoFull(rsaPublicKey,privatekey,pwd);
                hash=set['mac'];
                enc=set['Encryption'];
                priv=set['priv'];
                var result = RSAEncrypt(rsaPublicKey,pwd);
                // $("#password").val(result.toString(16));
                $("#password").val(result.toString());
                var str ="原始："+pwd+",加密后："+result;
                // alert(str);
                return true;
            });
        })
    </script>
</head>
<body>
<div class="rg_layout">
    <div class="rg_left">
        <p>新用户注册</p>
        <p>USER REGISTER</p>

    </div>
    <div class="rg_center">
        <div class="rg_form">
            <form id="registerForm" action="registUserServlet" method="post">
                <table>
                    <tr>
                        <td class="td_left"><label for="username">用户名</label></td>
                        <td class="td_right"><input type="text" name="username" id="username" placeholder="请输入用户名" required="required"></td>
                    </tr>
                    <tr>
                        <td class="td_left"><label for="password">密码</label></td>
                        <td class="td_right"><input type="password" name="password" id="password" placeholder="请输入密码" required="required"></td>
                    </tr>
                    <tr>
                        <td class="td_left"><label for="password">确认密码</label></td>
                        <td class="td_right"><input type="password" name="finalpass" id="finalpass" placeholder="请再次输入密码" required="required"></td>
                    </tr>
                    <tr>
                        <td class="td_left"><label for="username">姓名</label></td>
                        <td class="td_right"><input type="text" name="name" id="name" placeholder="请输入姓名" required="required"></td>
                    </tr>
                    <tr>
                        <td class="td_left"><label for="age">年龄</label></td>
                        <td class="td_right"><input type="text" name="age" id="age" placeholder="请输入年龄" required="required"></td>
                    </tr>
                    <tr>
                        <td class="td_left"><label for="email">Email</label></td>
                        <td class="td_right"><input type="email" name="email" id="email" placeholder="请输入邮箱" required="required"></td>
                    </tr>
                    <tr>
                        <td class="td_left"><label for="telephone">telephone</label></td>
                        <td class="td_right"><input type="text" name="telephone" id="telephone" placeholder="请输入电话号码" required="required"></td>
                    </tr>
                    <tr>
                        <td class="td_left"><label for="address">住址</label></td>
                        <td class="td_right"><input type="text" name="address" id="address" placeholder="请输入家庭住址" required="required"></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center"><input type="submit" id="btn_sub" value="submit" required="required"></td>
                    </tr>
                </table>
            </form>
        </div>

    </div>
    <div class="rg_right">
        <p>已有账号<a href="login">立即登陆</a></p>
        <p>LOGIN NOW</p>
    </div>
</div>
</form>
</body>
</html>