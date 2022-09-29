package com.likaiyuan.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likaiyuan.Crypyto.AESUtils;
import com.likaiyuan.Crypyto.RSAUtils;
import com.likaiyuan.Crypyto.SHA256;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/test")
public class test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> map=new HashMap<>();
        ObjectMapper mapper=new ObjectMapper();
        String Enc_priv=req.getParameter("private");
        String hash=req.getParameter("hash");
        String enc=req.getParameter("pwd");
        List<String> result=Decry(Enc_priv,hash,enc);
        System.out.println("解密结果为");
        System.out.println(result.get(1));
        map.put("flag",result.get(0));
        mapper.writeValue(resp.getWriter(),map);
    }
    private List<String> Decry(String Enc_priv,String hash,String enc)
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
