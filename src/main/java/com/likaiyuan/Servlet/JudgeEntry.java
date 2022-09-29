package com.likaiyuan.Servlet;

import com.likaiyuan.Crypyto.AESUtils;
import com.likaiyuan.Crypyto.RSAUtils;
import com.likaiyuan.Crypyto.SHA256;
import com.likaiyuan.Crypyto.SHACoder;
import com.likaiyuan.Dao.Daoimply2;
import com.likaiyuan.Dao.Daoimply3;
import com.likaiyuan.pojo.BankUser;

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
import java.util.List;

//淘宝调用此程序，若信息无误，跳转至前端输入密码的界面
@WebServlet("/judge")
public class JudgeEntry extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
    /*
     *淘宝将请求post到此处，进行安全性检查,目前1.0版本直接转发即可
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        var params = {
          "encryptedOI": encryptedOI,
          "encryptedPI": encryptedPI,
          "encryptedBigIntegerSymKey1Str": encryptedBigIntegerSymKey1Str, // 16进制
          "encryptedBigIntegerSymKey2Str": encryptedBigIntegerSymKey2Str, // 16进制
          "OIMD": OIMD,
          "PIMD": PIMD,
          "DS": DS,
      }
         */

        System.out.println(req.getParameter("encryptedPI"));
        System.out.println(req.getParameter("encryptedBigIntegerSymKey2Str"));
        System.out.println(req.getParameter("OIMD"));
        System.out.println(req.getParameter("PIMD"));
        System.out.println(req.getParameter("DS"));
        System.out.println(req.getParameter("consumerModulusString"));
        //得到数据

        String encryptedPI = req.getParameter("encryptedPI");
        String encryptedBigIntegerSymKey2Str = req.getParameter("encryptedBigIntegerSymKey2Str");
        String OIMD = req.getParameter("OIMD");
        String PIMD = req.getParameter("PIMD");
        String DS = req.getParameter("DS");
        String consumerModulusString=req.getParameter("consumerModulusString");
        System.out.println("成功进入判断程序");
        System.out.println("成功进行转发");
        //得到的两个都是银行卡号
        System.out.println(req.getParameter("consumer"));
        System.out.println(req.getParameter("seller"));
        System.out.println(req.getParameter("orderNum"));
        System.out.println(req.getParameter("money"));
        //得到前端传递的数据
//        String username=req.getParameter("consumer");
//        String othername=req.getParameter("seller");
//        String money=req.getParameter("money");
//        int realmoney=Integer.parseInt(money);
        //解析数据
        RSAPrivateKey rsaPrivateKey = null;
        String privExpString = "2025766971890293820997693703792458637920098280733197226888469376776100882509330161094468906207268811159996822826036920640069187826398802524593148203508425545865583028204793767615995521588894236793126204546431524143744460732675292611185551263373534122129284496906215700353647166980140244913696947916901741580064523277772557052616025226125265346813360460370432504662986426862651323615995173876700237851268613418279782759718028178763011015436327986696090849852726138244812167682672585990291069490713274137495572196139103344650455976160076943537310957937887434093174022085718185003538372122175894602017115389086587858945";
        String modulusString = "17126250004743832062271136773148395479020830885502005502914037351106207886611709335351936107598784323657470559539471319399924453377025067215461965146198617775979452388990914492808242840347182868512785354406538028613078008647747762107748642046918383096231542579172169550319527139109578332160598152171051269083425998865133320894969576039873247822018688656622604184680970205883110228331547811334192527137719464772754014068724697230281388138710121334158719304734473613859928862649571999239568477770045917446818093356997448298253301531599196598292045171796366002655681430019570216715707274055068984341457899188248571344721";
        try {
            rsaPrivateKey = RSAUtils.generateRSAPrivateKey(new BigInteger(privExpString), new BigInteger(modulusString));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        String symKey2 = RSAUtils.decrypt(rsaPrivateKey, new BigInteger(encryptedBigIntegerSymKey2Str, 16));
        try {
            rsaPrivateKey = RSAUtils.generateRSAPrivateKey(new BigInteger("65537"), new BigInteger(consumerModulusString));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        String PI = AESUtils.AESDecrypt(symKey2, encryptedPI);
        System.out.println("解密的PI为");
        System.out.println(PI);
        System.out.println("解密的symkey2");
        System.out.println(symKey2);
//        System.out.println("real PI is");
//        System.out.println(req.getParameter("PI"));
//        System.out.println(req.getParameter("symKey2"));
        String PIMD2=null;
        try {
            PIMD2= SHACoder.sha256(PI);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!PIMD.equals(PIMD2))
        {
            System.out.println("PIMD:" + PIMD);
            System.out.println("PIMD2:" + PIMD2);
            System.out.println("检测到PIMD被篡改！");
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<script>window.alert('风险控制系统监测到攻击!');</script>");
            return;
        }
        String POMD = RSAUtils.decrypt(rsaPrivateKey, new BigInteger(DS));
        System.out.println("解密得到的POMD为");
        System.out.println(POMD);
        try {
            String POMD2 = SHACoder.sha256(PIMD + OIMD);
            System.out.println("hash出的POMD为");
            System.out.println(POMD2);
            if(!POMD.equals(POMD2))
            {
                System.out.println("PIMD:" + POMD);
                System.out.println("PIMD2:" + POMD2);
                System.out.println("检测到POMD被篡改！");
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().println("<script>window.alert('风险控制系统监测到攻击!');</script>");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("验证通过，开始转发");
        System.out.println(PI);
        String [] arr=PI.split("&");
        String myID=arr[0].substring(12,arr[0].length());
        String ordernum=arr[1].substring(8,arr[1].length());
        String money=arr[2].substring(6,arr[2].length());
        System.out.println(req.getParameter("sellerUsername"));
        System.out.println(req.getParameter("consumerUsername"));
        System.out.println(myID);
        System.out.println(ordernum);
        System.out.println(money);
        String username=req.getParameter("consumerUsername");
        String othername=req.getParameter("sellerUsername");
        double it=Double.parseDouble(money);
        int monplus=(int) it;
        System.out.println("username为");
        System.out.println(username);
        //搜索得到对方的卡号
        Daoimply2 jdbc=new Daoimply2();
        List<BankUser> list=new ArrayList<>();
        list=jdbc.findByusername(othername);
        String cardID=list.get(0).getCardID();
//        //如若无误，将请求转发至输入界面
        req.setAttribute("username",username);
        req.setAttribute("ID",cardID);
        req.setAttribute("ordernumber",ordernum);
        req.setAttribute("money",monplus);
        req.getRequestDispatcher("payentry.jsp").forward(req,resp);
    }
}
