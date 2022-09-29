<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="./labjs/jsbn.js"></script>
    <script type="text/javascript" src="./labjs/RSAUtils.js"></script>
    <script type="text/javascript" src="jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="./labjs/aes.js"></script>
	<script type="text/javascript" src="./labjs/aeslib.js"></script>
	<script type="text/javascript" src="./labjs/sha_dev.js"></script>
	<script type="text/javascript" src="./labjs/banksignature.js"></script>
<title>Login</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="./css/login.css">
	<style>
		table{
			margin: auto;
		}
	</style>

</head>
<body>
<div id="login_box">
	<h2>lOGIN </h2>
	<div id="form">
		<form id="loginform"  action="login" method="POST">
			<fieldset>
				<div id="input_box1">
					<i class="fa fa-user" aria-hidden="true"></i>
					<input class="in" type="text" name="username" id="username" placeholder="Username">
				</div>
				<div id="input_box">
					<i class="fa fa-key" aria-hidden="true"></i>
					<input class="in" type="password" name="pwd" id="pwd" placeholder="Password">
				</div>
				<div id="input_box2">
					<i class="fa fa-key" aria-hidden="true"></i>
					<input class="in" type="password" name="check" id="check" placeholder="验证码">
				</div>
				<table>
				<tr>
					<td colspan="2"><img id="img" src="check"></td>
				</tr>
				</table>
			    <input type="button" id="loginbtn" value="登录"/>

			</fieldset>

		</form>
	</div>

	<div id="Sign">
		<a href="a">忘记密码?</a>
	</div>
</div>

<%--	<form id="loginform"  action="login" method="POST">--%>
<%--		<fieldset>--%>
<%--			<legend>登陆</legend>--%>
<%--            用户名：<input type="user" name="username" id="'user" />--%>
<%--			密码：<input type="password" name="pwd" id="pwd" />--%>
<%--			<div id="resultinfo"></div>--%>
<%--			<button id="loginbtn">登陆</button>--%>
<%--		</fieldset>--%>
<%--	</form>--%>
</body>
<script type="text/javascript">
	// var pwd;
	var pwd = "123456";
	var user="haha";
	//得到m,e
	var publicModulusString = '<%=request.getSession().getAttribute("module")%>';
	var publicExponentString = '<%=request.getSession().getAttribute("exponent")%>';
	<%--var checkcode='<%=request.getSession().getAttribute("checkcode")%>';--%>
	// var publicModulus = new BigInteger(publicModulusString, 16);
	// var publicExponent = new BigInteger(publicExponentString, 16);
	// var rsaPublicKey = new RSAPublicKey(publicExponent, publicModulus);
	//生成私钥
	var privatekey=AESGenerateKey(32);
	var priv;
	var result;
	var check;
	// console.log("privatekey");
	// console.log(privatekey);
	// console.log(checkcode);
	$(function(){
		$("#loginbtn").click(function(){
			//get publickey
			var publicModulus = new BigInteger(publicModulusString);
			var publicExponent = new BigInteger(publicExponentString);
			var rsaPublicKey = new RSAPublicKey(publicExponent, publicModulus);
			console.log("login button");
			//得到密码值和用户名
			<%--checkcode='<%=request.getSession().getAttribute("checkcode")%>';--%>
			<%--console.log(checkcode);--%>
			pwd = $("#pwd").val();
			user=$("#username").val();
			check=$("#check").val();
			//使用前端hash函数进行测试：
			shaObj1 = new jsSHA("SHA-256", "TEXT");
			shaObj1.update(pwd.toString());
			hash1 = shaObj1.getHash("HEX");
			//用公钥加密私钥
			priv=RSAEncrypt(rsaPublicKey,privatekey).toString();//priv为加密后的私钥
			//用私钥加密密码(此密码已经hash过)
			result=AESEncrypt(privatekey,hash1);
			// var result = RSAEncrypt(rsaPublicKey,pwd);
			if(pwd==null||pwd==""){
				alert("请填写密码");
				return false;
			}

			$.ajax({
				url:'login',//发送请求的地址
				type:'POST',  //请求方式，默认为GET
				dataType:"json",//返回值类型
				data:{
					"username":user, //用户名。
					"pwd":result,//用私钥加密的密码
					'private':priv, //用公钥加密后的私钥
					"hash":hash1,//密码散列后的值
					"check":check
				},
				success: (data)=>{
					// alert(data);
					alert(data.flag);
					if(data.flag)
					{
						location.href="mainpage";
					}
					else{
						alert("请检查密码或验证码！！");
					}
				}
			});
			alert("传输成功");
			return true;
		});
	})
</script>
<script>
	window.onload=function (){
		document.getElementById("img").onclick=function (){

			this.src="check?time="+new Date().getTime();

		};
	}
</script>
</html>
