package xiaogu.cash.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xiaogu.cash.dao.UserMapper;
import xiaogu.cash.service.WechatService;
import xiaogu.cash.util.MessageUtil;
import xiaogu.cash.util.PropertiesUtil;
import xiaogu.cash.wechat.BaseMessage;
import xiaogu.cash.wechat.TextMessage;

@Service
public class WechatServiceImpl implements WechatService {
	private static Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);
	@Autowired
	private UserMapper userMapper;

	@Override
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = getDefaultRespContent();
			logger.info("respContent = " + respContent);

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			
			logger.info("requestMap = " + requestMap);
			// 消息类型
			String msgType = requestMap.get("MsgType");

			TextMessage textMessage = new TextMessage();
			textMessage = (TextMessage) setBaseInfo(textMessage, requestMap);
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				String eventKey = requestMap.get("EventKey");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
					
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					
		            String content = getContent(eventKey);
		            textMessage.setContent(content);
		            respMessage = MessageUtil.textMessageToXml(textMessage);
				} else {
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
			} else {
				textMessage.setContent(getWelcomeRespContent( ));
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}

			return respMessage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据用户扫描二维码时，根据场景码不同得到推送的内容
	 * @param eventKey
	 * @return
	 */
	private String getContent(String eventKey) {
		if (StringUtils.isBlank(eventKey)) {
			String defaultRespContent = PropertiesUtil.getValue("defaultRespContent");
			return defaultRespContent;
		}
		String content = PropertiesUtil.getValue(eventKey);
		if (StringUtils.isBlank(content)) {
			return getDefaultRespContent();
		}
		return content;
		
	}
	
	
	private String getDefaultRespContent() {
		String defaultRespContent = PropertiesUtil.getValue("defaultRespContent");
		return defaultRespContent;
	}
	
	private String getWelcomeRespContent() {
		String defaultRespContent = PropertiesUtil.getValue("welcomeContent");
		return defaultRespContent;
	}
	
	
	private Object setBaseInfo(BaseMessage baseMessage,Map<String, String> requestMap) {
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		baseMessage.setToUserName(fromUserName);
		baseMessage.setFromUserName(toUserName);
		baseMessage.setCreateTime(new Date().getTime());
		baseMessage.setFuncFlag(0);
		return baseMessage;
	}

}
