package xiaogu.cash.util;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class WechatUtil {
	private static Logger logger = LoggerFactory.getLogger(WechatUtil.class);

	// 与接口配置信息中的Token要一致
	public static String token = PropertiesUtil.getValue("token");
	// appId
	public static final String appId = PropertiesUtil.getValue("appId");
	// appSecret
	public static final String appSecret = PropertiesUtil.getValue("appSecret");

	public final static String access_token_url = 
							PropertiesUtil.getValue("access_token_url");

	public final static String menu_create_url = PropertiesUtil.getValue("menu_create_url");
	/**
	 * 微信网页授权，获取code的api
	 */
	public static final String getCodeApi = PropertiesUtil.getValue("getCodeApi");
	/**
	 * 微信网页授权，回调URL
	 */
	public final static String getCodeRedirectUrl = 
						PropertiesUtil.getValue("getCodeRedirectUrl");

	/**
	 * 微信网页授权，抽取openid的api
	 */
	public final static String getOpenIdApi = PropertiesUtil.getValue("getOpenIdApi");
	
	/**
	 * 通过code换取网页授权access_toke 的 url
	 */
	public static final String GET_SNS_TOKEN = 
			"https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	
	
	public static final String GET_ACCESS_TOKEN_FROM_CODE = 
			"https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	
	
	public static final String GET_USER_INFO = 
			"https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	
	
	private  static long getAccessTokenTime = 0;
	
	private static String accessToken = "";
	
	private static long getJsapiTicketTime = 0;
	
	private static String jsapiTicket = "";
	
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-SSS");
	
	/**
	 * JSSDK获取网页分享的ticket
	 * @return
	 */
	public static String getJsapiTicket() {
		if (getJsapiTicketTime == 0) {
			_getJsapiTicket();
		} else	if ((new Date().getTime() - getJsapiTicketTime) / 1000 > 3600) {
			_getJsapiTicket();
		}
		return jsapiTicket;
	}
	
	private static void _getJsapiTicket() {
		String getTicketUrl = PropertiesUtil.getValue("getTicketUrl");
		String accessToken = getAccessToken();
		getTicketUrl = getTicketUrl.replace("ACCESS_TOKEN", accessToken);
		
		GetMethod get = new GetMethod(getTicketUrl);
		
		JSONObject jsonObject = executeMethod(get);
		if (null != jsonObject) {
			jsapiTicket = jsonObject.getString("ticket");
			getJsapiTicketTime = new Date().getTime();
			logger.info("访问jsapiTicket 的时间    " + dateFormat.format(new Date()));
		}

	}
	
	
	/**
	 * 通用的调用微信接口方法，返回JSONObject
	 * @param method   GetMethod 或 PostMethod
	 * @return
	 */
	public static JSONObject executeMethod(HttpMethod method) {
		HttpClient client = new HttpClient();
		client.getParams().setContentCharset("utf-8");
		String executeMethodResult = "";
		try {
			client.executeMethod(method);
			Reader r = new InputStreamReader(
					method.getResponseBodyAsStream(), "utf-8");
			executeMethodResult = IOUtils.toString(r);
			logger.info("executeMethodResult = " + executeMethodResult);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			method.releaseConnection();
		}
		JSONObject jsonObject = JSONObject.fromObject(executeMethodResult);
		return jsonObject;
	}

	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		StringBuilder strDigest = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			strDigest.append(byteToHexStr(byteArray[i]));
		}
		return strDigest.toString();
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static String getAccessToken() {
		if (getAccessTokenTime == 0) {
			getToken();
		} else	if ((new Date().getTime() - getAccessTokenTime) / 1000 > 3600) {
			getToken();
		}
		return accessToken;
	}
	
	/**
	 * 访问微信服务器获取 accessToken
	 * @return
	 */
	private static void getToken() {
		String requestUrl = access_token_url.replace("APPID", appId).replace(
				"APPSECRET", appSecret);
		GetMethod get = new GetMethod(requestUrl);
		JSONObject jsonObject = executeMethod(get);
		if (null != jsonObject) {
			accessToken = jsonObject.getString("access_token");
			getAccessTokenTime = new Date().getTime();
			logger.info("访问accessToken 的时间    " + dateFormat.format(new Date()));
		}
	}


	public static String getOpenId(String code) {
		String getOpenidUrl = getOpenIdApi.replace("APPID", appId)
				.replace("SECRET", appSecret).replace("CODE", code);
		
		GetMethod get = new GetMethod(getOpenidUrl);
		JSONObject jsonObject = executeMethod(get);
		return jsonObject.getString("openid");
	}
	
	
	
	public static String getAccessTicket(String sceneId) throws Exception{
		String ticket = "{\"action_name\": \"QR_LIMIT_SCENE\",\"action_info\": {\"scene\": {\"scene_id\": \"" + sceneId + "\"}}}";
		
		String accessTokenUrl = PropertiesUtil.getValue("access_token_url");
		
		accessTokenUrl = accessTokenUrl.replace("APPID", appId).replace("APPSECRET", appSecret);
    	
		GetMethod get = new GetMethod(accessTokenUrl);
		JSONObject object = executeMethod(get);
		
		String accessToken = object.getString("access_token");
		
		
		String getTicket = PropertiesUtil.getValue("getTicket");
		String finalGetTicket = getTicket + accessToken;
		
		PostMethod post = new PostMethod(finalGetTicket);
		post.setRequestEntity(new StringRequestEntity(ticket, null, null));
		object = executeMethod(post);
		return object.getString("ticket");
	}
	
	
	public static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

	public static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /** 
     * emoji表情转换(hex -> utf-16) 
     *  
     * @param hexEmoji 
     * @return 
     */  
    public static String emoji(int hexEmoji) {  
        return String.valueOf(Character.toChars(hexEmoji));  
    } 
    
    
    
    public static JSONObject getSnsToken(String code) {
    	String url = GET_SNS_TOKEN.replace("APPID", appId)
    			.replace("SECRET", appSecret)
    			.replace("CODE", code);
    	logger.info("getSnsToken-url = " + url);
    	GetMethod getMethod = new GetMethod(url);
    	
    	return executeMethod(getMethod);
    }
    public static JSONObject getAccessTokenFromCode(String refresh_token) {
    	String url = GET_ACCESS_TOKEN_FROM_CODE.
    			replace("APPID", appId).replace("REFRESH_TOKEN", refresh_token);
    	GetMethod getMethod = new GetMethod(url);
    	return executeMethod(getMethod);
    }
    //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
    public static JSONObject getUserInfo(String accessToken,String openId) {
    	String url = GET_USER_INFO.
    			replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
    	GetMethod getMethod = new GetMethod(url);
    	return executeMethod(getMethod);
    }
    
    /**
	 * 解析微信通知xml
	 * 
	 * @param xml
	 * @return
	 */
	public static SortedMap<String,String> parseXmlToList2(String xml) {
		SortedMap<String,String> retMap = new TreeMap<String,String>();
		try {
			StringReader read = new StringReader(xml);
			InputSource source = new InputSource(read);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(source);
			//返回状态码
			NodeList nl = document.getElementsByTagName("return_code");
			retMap.put("return_code", nl.item(0).getTextContent());
			//返回信息
			nl = document.getElementsByTagName("return_msg");
			if(nl != null && nl.item(0) != null){
				retMap.put("return_msg", nl.item(0).getTextContent());
			}
			//公众号appid
			nl = document.getElementsByTagName("appid");
			if(nl != null && nl.item(0) != null){
				retMap.put("appid", nl.item(0).getTextContent());
			}
			//商户订单号
			nl = document.getElementsByTagName("mch_id");
			if(nl != null && nl.item(0) != null){
				retMap.put("mch_id", nl.item(0).getTextContent());
			}
			//设备号
			nl = document.getElementsByTagName("device_info");
			if(nl != null && nl.item(0) != null){
				retMap.put("device_info", nl.item(0).getTextContent());
			}
			//随机字符串
			nl = document.getElementsByTagName("nonce_str");
			if(nl != null && nl.item(0) != null){
				retMap.put("nonce_str", nl.item(0).getTextContent());
			}
			//签名
			nl = document.getElementsByTagName("sign");
			if(nl != null && nl.item(0) != null){
				retMap.put("sign", nl.item(0).getTextContent());
			}
			//业务结果
			nl = document.getElementsByTagName("result_code");
			if(nl != null && nl.item(0) != null){
				retMap.put("result_code", nl.item(0).getTextContent());
			}
			//错误代码
			nl = document.getElementsByTagName("err_code");
			if(nl != null && nl.item(0) != null){
				retMap.put("err_code", nl.item(0).getTextContent());
			}
			//错误代码描述
			nl = document.getElementsByTagName("err_code_des");
			if(nl != null && nl.item(0) != null){
				retMap.put("err_code_des", nl.item(0).getTextContent());
			}
			//openid
			nl = document.getElementsByTagName("openid");
			if(nl != null && nl.item(0) != null){
				retMap.put("openid", nl.item(0).getTextContent());
			}
			//用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
			nl = document.getElementsByTagName("is_subscribe");
			if(nl != null && nl.item(0) != null){
				retMap.put("is_subscribe", nl.item(0).getTextContent());
			}
			//交易类型
			nl = document.getElementsByTagName("trade_type");
			if(nl != null && nl.item(0) != null){
				retMap.put("trade_type", nl.item(0).getTextContent());
			}
			//银行类型
			nl = document.getElementsByTagName("bank_type");
			if(nl != null && nl.item(0) != null){
				retMap.put("bank_type", nl.item(0).getTextContent());
			}
			//订单总金额，单位为分
			nl = document.getElementsByTagName("total_fee");
			if(nl != null && nl.item(0) != null){
				retMap.put("total_fee", nl.item(0).getTextContent());
			}
			//费用类型
			nl = document.getElementsByTagName("fee_type");
			if(nl != null && nl.item(0) != null){
				retMap.put("fee_type", nl.item(0).getTextContent());
			}
			//现金支付金额订单现金支付金额
			nl = document.getElementsByTagName("cash_fee");
			if(nl != null && nl.item(0) != null){
				retMap.put("cash_fee", nl.item(0).getTextContent());
			}
			//现金支付货币类型
			nl = document.getElementsByTagName("cash_fee_type");
			if(nl != null && nl.item(0) != null){
				retMap.put("cash_fee_type", nl.item(0).getTextContent());
			}
			//代金券或立减优惠金额
			nl = document.getElementsByTagName("coupon_fee");
			if(nl != null && nl.item(0) != null){
				retMap.put("coupon_fee", nl.item(0).getTextContent());
			}
			//代金券或立减优惠使用数量
			nl = document.getElementsByTagName("coupon_count");
			if(nl != null && nl.item(0) != null){
				retMap.put("coupon_count", nl.item(0).getTextContent());
			}
			if(retMap.get("coupon_count") != null){
				int count = Integer.parseInt(retMap.get("coupon_count").toString());
				for(int i = 0; i < count; ++i){
					String couponId = "coupon_id_" + i;
					String couponFee = "coupon_fee_" + i;
					String couponType = "coupon_type_" + i;
					//代金券或立减优惠ID
					nl = document.getElementsByTagName(couponId);
					if(nl != null && nl.item(0) != null){
						retMap.put(couponId, nl.item(0).getTextContent());
					}
					//单个代金券或立减优惠支付金额
					nl = document.getElementsByTagName(couponFee);
					if(nl != null && nl.item(0) != null){
						retMap.put(couponFee, nl.item(0).getTextContent());
					}
					//订单使用代金券时有返回（取值：CASH、NO_CASH）
					nl = document.getElementsByTagName(couponType);
					if(nl != null && nl.item(0) != null){
						retMap.put(couponType, nl.item(0).getTextContent());
					}
				}
			}
			//微信支付订单号
			nl = document.getElementsByTagName("transaction_id");
			if(nl != null && nl.item(0) != null){
				retMap.put("transaction_id", nl.item(0).getTextContent());
			}
			//商户订单号
			nl = document.getElementsByTagName("out_trade_no");
			if(nl != null && nl.item(0) != null){
				retMap.put("out_trade_no", nl.item(0).getTextContent());
			}
			//商家数据包，原样返回
			nl = document.getElementsByTagName("attach");
			if(nl != null && nl.item(0) != null){
				retMap.put("attach", nl.item(0).getTextContent());
			}
			//支付完成时间
			nl = document.getElementsByTagName("time_end");
			if(nl != null && nl.item(0) != null){
				retMap.put("time_end", nl.item(0).getTextContent());
			}
			//预支付交易会话标识
			nl = document.getElementsByTagName("prepay_id");
			if(nl != null && nl.item(0) != null){
				retMap.put("prepay_id", nl.item(0).getTextContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}
}
