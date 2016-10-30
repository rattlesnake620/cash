package xiaogu.cash.util;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import xiaogu.cash.wechat.Button;
import xiaogu.cash.wechat.CommonButton;
import xiaogu.cash.wechat.ComplexButton;
import xiaogu.cash.wechat.Menu;
import xiaogu.cash.wechat.ViewButton;

public class MenuUtil {
	private static Logger logger = Logger.getLogger(MenuUtil.class);

	
	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	@SuppressWarnings("deprecation")
	public static int createMenu(Menu menu) {
		int result = 0;
		String accessToken = WechatUtil.getAccessToken();
		// 拼装创建菜单的url
		String url = WechatUtil.menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		logger.info(jsonMenu);
		PostMethod post = new PostMethod(url);
		post.setRequestBody(jsonMenu);
		JSONObject jsonObject = WechatUtil.executeMethod(post);
		if (null != jsonObject) {
			result = jsonObject.getInt("errcode");
			if (result != 0) {
				logger.error("创建菜单失败 errcode: "
						+ jsonObject.getInt("errcode")
						+ "  errmsg: "
						+ jsonObject.getString("errmsg"));
			}
		}
		return result;
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private static Menu getMenu() throws UnsupportedEncodingException {
		String weixinLogin = WechatUtil.getCodeApi.replace("APPID", WechatUtil.appId)
				.replace("REDIRECT_URI", WechatUtil.getCodeRedirectUrl);
		ViewButton recommend = new ViewButton();
		recommend.setName(new String("推荐".getBytes(),"UTF-8"));
		recommend.setType("view");
		recommend.setUrl("http://xt.xiaogu365.com/education/index.html#!/");
		
		ViewButton course = new ViewButton();
		course.setName(new String("课程".getBytes(),"UTF-8"));
		course.setType("view");
		course.setUrl("http://xt.xiaogu365.com/education/index.html#!/lesson");
		
		ViewButton user = new ViewButton();
		user.setName(new String("我的".getBytes(),"UTF-8"));
		user.setType("view");
		user.setUrl(weixinLogin);
		
		Menu menu = new Menu();
		menu.setButton(new Button[] {recommend,course,user});
		return menu;
	}
	
	static Menu getMenu42016NewYear() {
		String lendUrl = WechatUtil.getCodeApi.replace("APPID", WechatUtil.appId)
				.replace("REDIRECT_URI", WechatUtil.getCodeRedirectUrl)
				.replace("STATE", "lend");
		ViewButton rapidLend = new ViewButton();
		rapidLend.setName("快借快还");
		rapidLend.setType("view");
		rapidLend.setUrl(lendUrl);

		String loanUrl = WechatUtil.getCodeApi.replace("APPID", WechatUtil.appId)
				.replace("REDIRECT_URI", WechatUtil.getCodeRedirectUrl)
				.replace("STATE", "loan");
		System.out.println("loanUrl = " + loanUrl);
		ViewButton loanBtn = new ViewButton();
		loanBtn.setName("学生贷");
		loanBtn.setType("view");
		loanBtn.setUrl(loanUrl);

		

		ComplexButton mainBtn = new ComplexButton();
		mainBtn.setName("我要借款");
		mainBtn.setSub_button(new Button[] { rapidLend, loanBtn });
		
		
//		ViewButton zanzhubtn = new ViewButton();
//		zanzhubtn.setName("社团赞助");
//		zanzhubtn.setType("view");
//		zanzhubtn.setUrl("http://form.mikecrm.com/f.php?t=OjrJxR");
		
		
		ViewButton jianzhi = new ViewButton();
		jianzhi.setName("校园兼职");
		jianzhi.setType("view");
		jianzhi.setUrl("http://m.woxin100.com:81/activity/zhaomuFenxiang.do");
		
		
		ViewButton  zhenggao = new ViewButton();
		zhenggao.setName("征稿启事");
		zhenggao.setType("view");
		String zhenggaoUrl = "http://mp.weixin.qq.com/s?__biz=MzA5MDQzNDU5Ng==&mid=400185752&idx=1&sn=63c4fc25b52dc9bf0d3cdf3dfabed4cd&scene=0#wechat_redirect";
		zhenggao.setUrl(zhenggaoUrl);
		
		
		ViewButton jinlaizhuanqian = new ViewButton();
		jinlaizhuanqian.setName("进来赚钱");
		jinlaizhuanqian.setType("view");
		jinlaizhuanqian.setUrl("http://m.woxin100.com:81/activity/mosquito/play.do");
		

		ComplexButton huodongBtn = new ComplexButton();
		huodongBtn.setName("活动专区");
//		huodongBtn.setSub_button(new Button[] { zanzhubtn,jianzhi,zhenggao,lingqianma,jinlaizhuanqian });
//		huodongBtn.setSub_button(new Button[] { zanzhubtn,jianzhi,zhenggao,jinlaizhuanqian,qiangliuliang });
//		huodongBtn.setSub_button(new Button[] { zanzhubtn,jianzhi,zhenggao,jinlaizhuanqian });
		
		CommonButton contactUs = new CommonButton();
		contactUs.setName("联系我们");
		contactUs.setType("click");
		contactUs.setKey("contactUs");
		
		ViewButton qa = new ViewButton();
		qa.setName("热门问题");
		qa.setType("view");
		qa.setUrl("http://120.26.123.138:8090/webPage/hd/faq/index.html#0");
		
		
		ViewButton baodian = new ViewButton();
		baodian.setName("使用宝典");
		baodian.setType("view");
		baodian.setUrl("http://120.26.123.138:8090/webPage/hd/handbook/index.html");
		
		
		ViewButton qiandao = new ViewButton();
		qiandao.setName("签到有奖");
		qiandao.setType("view");
		qiandao.setUrl(loanUrl);
		
		ComplexButton aboutUs = new ComplexButton();
		aboutUs.setName("我要赚钱");
		aboutUs.setSub_button(new Button[] { zhenggao,jianzhi,qa,contactUs});
		
		String redPacketURL = WechatUtil.getCodeApi.replace("APPID", WechatUtil.appId)
				.replace("REDIRECT_URI", WechatUtil.getCodeRedirectUrl)
				.replace("STATE", "redPacket");
		System.out.println("redPacketURL = " + redPacketURL);
		ViewButton redPacketBtn = new ViewButton();
		redPacketBtn.setName("抢红包");
		redPacketBtn.setType("view");
		redPacketBtn.setUrl(redPacketURL);

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn, qiandao,aboutUs});
		return menu;		
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		Menu menu = getMenu();
		createMenu(menu);
//		System.out.println(emoji(0x1f435));
//		System.out.println(emoji(0x1f339));
//		System.out.println(emoji(0x1f61d));
	}
	
	public static String emoji(int hexEmoji) {  
        return String.valueOf(Character.toChars(hexEmoji));  
    }

}
