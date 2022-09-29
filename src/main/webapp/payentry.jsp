<%--
  Created by IntelliJ IDEA.
  User: paerxiusi
  Date: 2021/12/10
  Time: 20:58
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
        // function httpPost(URL, PARAMS) {
        //     var temp = document.createElement("form");
        //     temp.action = URL;
        //     temp.method = "post";
        //     temp.style.display = "none";
        //
        //     for (var x in PARAMS) {
        //         var opt = document.createElement("textarea");
        //         opt.name = x;
        //         opt.value = PARAMS[x];
        //         temp.appendChild(opt);
        //     }
        //     document.body.appendChild(temp);
        //     temp.submit();
        //     return temp;
        // }
        $(function(){
            $("#loginbtn").click(function(){
                password=$("#pwd").val();
                username=$("#name").val();
                money=$("#money").val();
                ordernumber=<%=request.getAttribute("ordernumber")%>;
                ID=<%=request.getAttribute("ID")%>;
                //前端对结果进行签名，加密
                console.log(ID);
                $.ajax({
                    url:'payentry',//发送请求的地址
                    type:'POST',  //请求方式，默认为GET
                    dataType:"json",//返回值类型
                    data:{
                        "username":username, //用户名。
                        "pwd":password,//用私钥加密的密码
                        "money":money,
                        "ordernumber":ordernumber,
                        "ID":ID
                    },
                    success: (data)=>{
                        // alert(data);
                        alert(data.flag);
                        if(data.flag)
                        {
                            alert("交易成功");
                            var date=data.transdate;
                            // alert(date);
                            location.href="success.jsp";
                        }
                        else{
                            alert("交易失败");
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
        <h1>Pay</h1>
        <div class="form">
            <div class="item">
                <i class="fa fa-user-circle" aria-hidden="true"></i>
                <input type="text" name="username" id="name" value=<%=request.getAttribute("username")%>>
            </div>
            <div class="item">
                <i class="fa fa-user-circle" aria-hidden="true"></i>
                <input type="text" name="money"  id="money" value=<%=request.getAttribute("money")%>>
            </div>
            <div class="item">
                <i class="fa fa-key" aria-hidden="true"></i>
                <input type="password" placeholder="password" name="password" id="pwd">
            </div>
            <div class="item">
                <i class="fa fa-key" aria-hidden="true"></i>
                <a href="vrfyCertificate.html">验证证书</a>
            </div>
        </div>
        <button type="button" id="loginbtn" >确认</button>
    </div>
</form>
</body>

<footer>
    <div id="message-box">Hello, I'm JiadeChen!</div>
</footer>

</html>
