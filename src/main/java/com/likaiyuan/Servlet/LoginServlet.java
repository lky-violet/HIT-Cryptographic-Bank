package com.likaiyuan.Servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.likaiyuan.Crypyto.AESUtils;
import com.likaiyuan.Crypyto.RSAUtils;
import com.likaiyuan.Crypyto.SHA256;
import com.likaiyuan.Dao.Daoimply1;
import com.likaiyuan.pojo.User;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        System.out.println("--------------");
    }

	/**
	 * 生成密钥，打开login.jsp页面
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("login get method");
		KeyPair key = null;
		if(request.getSession().getAttribute("key")==null) {
			System.out.println("第一次进入还未生成公钥！");
			try {
				key = RSAUtils.generateKeyPair(1024);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			request.getSession().setAttribute("key", key);
//			RSAPublicKey pkey = (RSAPublicKey) key.getPublic();
//			String module = pkey.getModulus().toString(16);
			String module="17126250004743832062271136773148395479020830885502005502914037351106207886611709335351936107598784323657470559539471319399924453377025067215461965146198617775979452388990914492808242840347182868512785354406538028613078008647747762107748642046918383096231542579172169550319527139109578332160598152171051269083425998865133320894969576039873247822018688656622604184680970205883110228331547811334192527137719464772754014068724697230281388138710121334158719304734473613859928862649571999239568477770045917446818093356997448298253301531599196598292045171796366002655681430019570216715707274055068984341457899188248571344721";
			System.out.println("module是：");
			System.out.println(module);
			request.getSession().setAttribute("module", module);
//			String empoent = pkey.getPublicExponent().toString(16);
			String empoent="65537";
			request.getSession().setAttribute("exponent", empoent);
			System.out.println("module:" + module);
			System.out.println("expoent:" + empoent);
		}
		else{
			System.out.println("公钥已经存在，无需再次生成");
		}
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	/**
	 * 解密登陆密码，跳转main.jsp
	 * 否则跳转error.jsp
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("into post method");
		System.out.println("得到的验证码为");
		Map<String,Object> map=new HashMap<>();
		ObjectMapper mapper=new ObjectMapper();
		System.out.println((String)request.getSession().getAttribute("checkcode"));
		String checkcode=(String)request.getSession().getAttribute("checkcode");
		String check=request.getParameter("check");
		if(!checkcode.equals(check))
		{
			map.put("flag",false);
			mapper.writeValue(response.getWriter(),map);
			return;
		}
		//得到公钥
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
		//得到前端传递的数据
		String privkey=request.getParameter("private");//得到了用RSA加密的私钥
		String pwd = request.getParameter("pwd");//得到了用私钥加密的密码
		String user=request.getParameter("username");//得到了用户名
		System.out.println("得到加密后的私钥为");
		System.out.println(privkey);
		//得到前端传递的hash后的值
		System.out.println("密码hash后：");
		System.out.println(request.getParameter("hash"));
		//用RSA解密得到私钥
		BigInteger cipherPassword = new BigInteger(privkey);
		privkey=RSAUtils.decrypt(deckey,cipherPassword);
		System.out.println("解密私钥结果是");
		System.out.println(privkey);
		//将得到的私钥写入session,以后就用私钥加密传输数据
		request.getSession().setAttribute("privkey",privkey);
		//用得到的公钥解密得到密码
		String result=AESUtils.AESDecrypt(privkey,pwd);
		System.out.println("解密密码的结果是");
		System.out.println(result);
		//测试后端hash函数是否和前端一致
//		String backhash= SHA256.sha256(result);
		System.out.println("后端得到的散列为");
		System.out.println(result);
//		System.out.println(backhash.equals(request.getParameter("hash")));
		//得到前端口输入的账户和密码
		request.getSession().setAttribute("personname",user);//向session中存储用户名
		Daoimply1 jdbc=new Daoimply1();//数据库操作
		System.out.println("username"+user);
		System.out.println("pwd:"+pwd);
		//和数据库存储的进行比对
		List<User> userlist=new ArrayList<>();
		User it=new User();
		userlist=jdbc.findByusername(user);
		String username;
		String password = "123";
		for(int i=0;i<userlist.size();i++)
		{
			it=userlist.get(i);
			username=it.getUsername();
			password=it.getPassword();
		}
		System.out.println("比较的password为");
		System.out.println(password);
		System.out.println(result);
		//进行页面跳转,返回比较结果
		if(password.equals(result))
		{
			System.out.println("我跳了");
			map.put("flag",true);
			mapper.writeValue(response.getWriter(),map);

//			request.getRequestDispatcher("main.jsp").forward(request, response);
		}
		else{
			System.out.println("我没跳");
			map.put("flag",false);
//			mapper.writeValueAsString(map);
			mapper.writeValue(response.getWriter(),map);
		}
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
