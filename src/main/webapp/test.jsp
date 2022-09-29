
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
                <input type="button" id="loginbtn" value="登录"/>
            </fieldset>

        </form>
    </div>

</div>

</body>
<script type="text/javascript">
    //得到m,e
    var publicModulusString ="17126250004743832062271136773148395479020830885502005502914037351106207886611709335351936107598784323657470559539471319399924453377025067215461965146198617775979452388990914492808242840347182868512785354406538028613078008647747762107748642046918383096231542579172169550319527139109578332160598152171051269083425998865133320894969576039873247822018688656622604184680970205883110228331547811334192527137719464772754014068724697230281388138710121334158719304734473613859928862649571999239568477770045917446818093356997448298253301531599196598292045171796366002655681430019570216715707274055068984341457899188248571344721";
    var publicExponentString = "65537";
    //生成私钥
    var privatekey=AESGenerateKey(32);
    var hash;
    var enc;
    var priv;
    console.log("privatekey");
    console.log(privatekey);
    $(function(){
        $("#loginbtn").click(function(){
            //get publickey
            var publicModulus = new BigInteger(publicModulusString);
            var publicExponent = new BigInteger(publicExponentString);
            var rsaPublicKey = new RSAPublicKey(publicExponent, publicModulus);
            console.log("login button");
            //得到密码值和用户名
            pwd = $("#pwd").val();
            user=$("#username").val();
            var set=EncryptoFull(rsaPublicKey,privatekey,pwd);
            hash=set['mac'];
            enc=set['Encryption'];
            priv=set['priv'];
            $.ajax({
                url:'test',//发送请求的地址
                type:'POST',  //请求方式，默认为GET
                dataType:"json",//返回值类型
                data:{
                    "username":user, //用户名。
                    "pwd":enc,//用私钥加密的密码
                    'private':priv, //用公钥加密后的私钥
                    "hash":hash//mac
                },
                success: (data)=>{
                    // alert(data);
                    alert(data.flag);
                }
            });
            alert("传输成功");
            return true;
        });
    })
</script>
</html>
