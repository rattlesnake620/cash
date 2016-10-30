package xiaogu.cash.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xiaogu.cash.service.WechatService;
import xiaogu.cash.util.PropertiesUtil;
import xiaogu.cash.util.ResourceUtil;
import xiaogu.cash.util.WechatUtil;

@Controller
@RequestMapping("wechat")
public class WechatController {
	private static Logger logger = Logger.getLogger(WechatController.class);

	@Autowired
	private WechatService wechatService;
	
	@Autowired
	private ResourceUtil resourceUtil;

	@RequestMapping(method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String token = PropertiesUtil.getValue("token");
		String appId = PropertiesUtil.getValue("appId");
		logger.info("token = "  + token);
		logger.info("appId = "  + appId);
		
		
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		logger.info("signature = " + signature + ",timestamp = " + timestamp
				+ ",nonce = " + nonce + ",echostr = " + echostr);
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		PrintWriter out = response.getWriter();
		if (WechatUtil.checkSignature(signature, timestamp, nonce)) {
			logger.info("微信验证签名成功");
			out.write(echostr);
		} else {
			logger.error("微信验证签名失败");
			out.write("error");
		}
		out.close();
	}

	@RequestMapping(method = RequestMethod.POST)
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 调用核心业务类接收消息、处理消息
		String respMessage = wechatService.processRequest(request);
		logger.info("respMessage = " + respMessage);
		PrintWriter out = response.getWriter();
		out.write(respMessage);
		out.close();
	}
}

