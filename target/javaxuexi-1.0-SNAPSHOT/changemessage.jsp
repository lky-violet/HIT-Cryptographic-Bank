<%--
  Created by IntelliJ IDEA.
  User: paerxiusi
  Date: 2021/12/8
  Time: 21:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>修改个人信息</title>
    <script src="jquery-3.6.0.min.js"></script>
    <script>
        //登陆函数
        $(function(){
            $("#sub").click(function(){
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
                address=$("#address").val();
                email=$("#email").val();
                $.ajax({
                    url:'changemessage',//发送请求的地址
                    type:'POST',  //请求方式，默认为GET
                    dataType:"json",//返回值类型
                    data:{
                        "age":age, //用户名。
                        "email":email,//用私钥加密的密码
                        'telephone':tele,//用公钥加密后的私钥
                        "address":address
                    },
                    success: (data)=>{
                        // alert(data);
                        alert(data.flag);
                        if(data.flag)
                        {
                            alert("信息修改成功！")
                        }
                        else{
                            alert("信息修改失败！");
                        }
                    }
                });
            });
        })
    </script>
</head>
<body>
<form action="info/info_modify" name="myform" method="post">
    <div align="center">
        <table width="450" class="table" cellpadding="0" cellspacing="0" border="0">
            <tbody>
            <tr>
                <td>邮   箱：</td>
                <td width="360" height="30">
                    <input type="text" name="email" id="email" placeholder="邮箱" required="required"/>
                </td>
            </tr>
            <tr>
                <td>年　　龄：</td>
                <td width="360" height="30">
                    <input type="text" name="age" id="age" placeholder="年龄" required="required"/>
                </td>
            </tr>
            <tr>
                <td>家庭住址：</td>
                <td width="360" height="30">
                    <input type="text" name="address" id="address" placeholder="家庭住址" required="required"/>
                </td>
            </tr>
            <tr>
                <td>联系电话：</td>
                <td width="360" height="30">
                    <input type="text" name="telephone" id="telephone" placeholder="电话号码" required="required"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td width="360" height="30">
                    <input type="button" id="sub" value="提交" />
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>
</body>
</html>
