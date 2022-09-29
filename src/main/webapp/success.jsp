<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="./labjs/jsbn.js"></script>
    <script type="text/javascript" src="./labjs/RSAUtils.js"></script>
    <script type="text/javascript" src="./labjs/aes.js"></script>
    <script type="text/javascript" src="./labjs/aeslib.js"></script>
    <script type="text/javascript" src="./labjs/sha_dev.js"></script>
    <script type="text/javascript" src="./labjs/banksignature.js"></script>
<title>Success</title>
</head>
<body>
<script>
    // var date=0;
   var date=<%=request.getSession().getAttribute("transdate")%>;
   var ordernum=<%=request.getSession().getAttribute("orderNUM")%>;
   console.log(date);
   console.log(ordernum);
   //商家的m
   var publicModulusString ="28867889892199772343817736083669157287911166996538687179269415616871217718262752368080400626317396972139406722240708920261583934984622810929579727248189747769178278918167949042461963425323321510596842320868457730225743493008317500664974134457466119546218112287463660199256269673578559983389667083498346632243627965274407049256412061228174333521289841241580679432832064492586086773749252589130598103889094230223804587824090511984909898578984539057083340762186324904157710792905689686517370506668459158914440777340979040775220401940386218716835451246077107660074953512949678022810109429100853469753848536162139171336157";
   var publicExponentString = "65537";
    //生成私钥
    var privatekey=AESGenerateKey(32);
    var hash1;
    var enc1;
    var hash2;
    var enc2;
    var priv;
    var publicModulus = new BigInteger(publicModulusString);
    var publicExponent = new BigInteger(publicExponentString);
    var rsaPublicKey = new RSAPublicKey(publicExponent, publicModulus);
    var set1=EncryptoFull(rsaPublicKey,privatekey,date);
    var set2=EncryptoFull(rsaPublicKey,privatekey,ordernum);
    hash1=set1['mac'];
    enc1=set1['Encryption'];
    hash2=set2['mac'];
    enc2=set2['Encryption'];
    priv=set1['priv'];
    function httpPost(URL, PARAMS) {
        var temp = document.createElement("form");
        temp.action = URL;
        temp.method = "post";
        temp.style.display = "none";

        for (var x in PARAMS) {
            var opt = document.createElement("textarea");
            opt.name = x;
            opt.value = PARAMS[x];
            temp.appendChild(opt);
        }
        document.body.appendChild(temp);
        temp.submit();
        return temp;
    }
    var params = {
        "hash1": hash1, //时间
        "enc1": enc1,
        "hash2":hash2,//ordernum
        "enc2":enc2,
        "priv":priv
    };
    httpPost("http://192.168.43.143:8080/cryptoMall/successPay", params);
</script>
</body>
</html>