package com.likaiyuan.Servlet;

import com.likaiyuan.Crypyto.AESUtils;
import com.likaiyuan.Crypyto.RSAUtils;
import com.likaiyuan.Crypyto.SHA256;
import com.likaiyuan.Dao.Daoimply1;
import com.likaiyuan.Dao.Daoimply2;
import com.likaiyuan.pojo.BankUser;
import com.likaiyuan.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet("/registUserServlet")
public class RegisterUserServelt extends HttpServlet {
    /*
     *用户将用户名密码传送到后端，后端解密后经过hash存入数据库。
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("into post method");
        //得到前端传递的数据
        String pwd = req.getParameter("password");
        String username=req.getParameter("username");
        String email=req.getParameter("email");
        String name=req.getParameter("name");
        String tele=req.getParameter("telephone");
        String age=req.getParameter("age");
        String address=req.getParameter("address");

        System.out.println("username"+username);
        System.out.println("pwd:"+pwd);
        System.out.println(email);
        System.out.println(address);
        System.out.println(age);
        System.out.println(tele);
        //RSA解密
//        KeyPair key = (KeyPair) req.getSession().getAttribute("key");
        String privexpstr="2025766971890293820997693703792458637920098280733197226888469376776100882509330161094468906207268811159996822826036920640069187826398802524593148203508425545865583028204793767615995521588894236793126204546431524143744460732675292611185551263373534122129284496906215700353647166980140244913696947916901741580064523277772557052616025226125265346813360460370432504662986426862651323615995173876700237851268613418279782759718028178763011015436327986696090849852726138244812167682672585990291069490713274137495572196139103344650455976160076943537310957937887434093174022085718185003538372122175894602017115389086587858945";
        BigInteger private_exponent=new BigInteger(privexpstr);
        String module="17126250004743832062271136773148395479020830885502005502914037351106207886611709335351936107598784323657470559539471319399924453377025067215461965146198617775979452388990914492808242840347182868512785354406538028613078008647747762107748642046918383096231542579172169550319527139109578332160598152171051269083425998865133320894969576039873247822018688656622604184680970205883110228331547811334192527137719464772754014068724697230281388138710121334158719304734473613859928862649571999239568477770045917446818093356997448298253301531599196598292045171796366002655681430019570216715707274055068984341457899188248571344721";
        BigInteger modulus=new BigInteger(module);
//        RSAPrivateKey deckey = (RSAPrivateKey) key.getPrivate();
        RSAPrivateKey privateKey=null;
        try {
            privateKey = RSAUtils.generateRSAPrivateKey(private_exponent,modulus);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        BigInteger cipherPassword = new BigInteger( req.getParameter("password"));
        String result=RSAUtils.decrypt(privateKey,cipherPassword);
        String hash= SHA256.sha256(result);
        System.out.println("解密的结果为：");
        System.out.println(result);
        //以下为数据库操作：
        Daoimply1 jdbc=new Daoimply1();
        Daoimply2 jdbc2=new Daoimply2();
        User user=new User();
        String path = null;
        user.setUsername(username);
        user.setPassword(hash);
        user.setEmail(email);
        user.setName(name);
        BankUser bankUser=new BankUser();
        bankUser.setUsername(username);
        bankUser.setAddress(address);
        bankUser.setTelephone(tele);
        bankUser.setAge(age);
        bankUser.setEmail(email);
        bankUser.setMoney(0);
        //为用户随机生成一个卡号
        String cardID=getFixLenthString(10);
        bankUser.setCardID(cardID);
        System.out.println("bankuser为：");
        System.out.println(bankUser);
        boolean k= false;
        try {
            k = jdbc.save(user);
            k=jdbc2.save(bankUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(k)
        {
            System.out.println("插入成功");
            path = "login.jsp";
        }
        else{
            System.out.println("插入失败");
            path = "error.jsp";
        }
        req.getRequestDispatcher(path).forward(req, resp);
    }
/*
 *此方法在初始化页面时调用，生成公私钥对，通过session传递给前端，方便前端进行加密。
 */
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println("register get method");
    //生成公钥。
    KeyPair key = null;
    if(request.getSession().getAttribute("key")==null) {
        System.out.println("还未生成公钥，现在生成");
        try {
            key = RSAUtils.generateKeyPair(1024);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("key", key);
        RSAPublicKey pkey = (RSAPublicKey) key.getPublic();
        String module = pkey.getModulus().toString(16);
        module="17126250004743832062271136773148395479020830885502005502914037351106207886611709335351936107598784323657470559539471319399924453377025067215461965146198617775979452388990914492808242840347182868512785354406538028613078008647747762107748642046918383096231542579172169550319527139109578332160598152171051269083425998865133320894969576039873247822018688656622604184680970205883110228331547811334192527137719464772754014068724697230281388138710121334158719304734473613859928862649571999239568477770045917446818093356997448298253301531599196598292045171796366002655681430019570216715707274055068984341457899188248571344721";
        System.out.println("module是：");
        System.out.println(module);
        request.getSession().setAttribute("module", module);
        String exponent = pkey.getPublicExponent().toString(16);
        exponent="65537";
        request.getSession().setAttribute("exponent", exponent);
        System.out.println("module:" + module);
        System.out.println("empoent:" + exponent);
    }
    else{
        System.out.println("公钥已经存在，无需生成");
    }
    //跳转页面
    request.getRequestDispatcher("Register.jsp").forward(request, response);
}
private static String getFixLenthString(int strLength) {

    Random rm = new Random();

    // 获得随机数
    double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

    // 将获得的获得随机数转化为字符串
    String fixLenthString = String.valueOf(pross);
    System.out.println("得到的随机数为");
    System.out.println(fixLenthString);
    // 返回固定的长度的随机数
    return fixLenthString.substring(2, strLength + 2);
    }
    private List<String> Decry(String Enc_priv, String hash, String enc)
    {
        List<String> result=new ArrayList<>();
        //先解密用公钥加密的私钥
        String privexpstr="2025766971890293820997693703792458637920098280733197226888469376776100882509330161094468906207268811159996822826036920640069187826398802524593148203508425545865583028204793767615995521588894236793126204546431524143744460732675292611185551263373534122129284496906215700353647166980140244913696947916901741580064523277772557052616025226125265346813360460370432504662986426862651323615995173876700237851268613418279782759718028178763011015436327986696090849852726138244812167682672585990291069490713274137495572196139103344650455976160076943537310957937887434093174022085718185003538372122175894602017115389086587858945";
        BigInteger private_exponent=new BigInteger(privexpstr);
        String module="17126250004743832062271136773148395479020830885502005502914037351106207886611709335351936107598784323657470559539471319399924453377025067215461965146198617775979452388990914492808242840347182868512785354406538028613078008647747762107748642046918383096231542579172169550319527139109578332160598152171051269083425998865133320894969576039873247822018688656622604184680970205883110228331547811334192527137719464772754014068724697230281388138710121334158719304734473613859928862649571999239568477770045917446818093356997448298253301531599196598292045171796366002655681430019570216715707274055068984341457899188248571344721";
        BigInteger modulus=new BigInteger(module);
        RSAPrivateKey deckey=null;
        try {
            deckey = RSAUtils.generateRSAPrivateKey(private_exponent,modulus);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        BigInteger cipherPassword = new BigInteger(Enc_priv);
        String privkey=RSAUtils.decrypt(deckey,cipherPassword);//得到解密后的私钥
        System.out.println("解密私钥结果是");
        System.out.println(privkey);
        //然后计算mac，看是否被篡改，如果被篡改，停止解密
        String tocompare= SHA256.sha256(enc);
        if(!hash.equals(tocompare))
        {
            result.add("false");
            result.add("0");
            return result;
        }
        //然后开始用私钥解密
        String decpwd= AESUtils.AESDecrypt(privkey,enc);
        System.out.println("解密密码的结果是");
        System.out.println(decpwd);
        result.add("true");
        result.add(decpwd);
        return result;
    }


}
