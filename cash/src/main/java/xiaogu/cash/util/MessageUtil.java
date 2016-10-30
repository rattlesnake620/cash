package xiaogu.cash.util;  
  
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import xiaogu.cash.wechat.Article;
import xiaogu.cash.wechat.BaseMessage;
import xiaogu.cash.wechat.NewsMessage;
import xiaogu.cash.wechat.TextMessage;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
  
/** 
 * 微信消息工具类
 */  
public class MessageUtil {
	private static Logger logger = Logger.getLogger(MessageUtil.class);
  
    /** 
     * 返回消息类型：文本 
     */  
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 返回消息类型：音乐 
     */  
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";  
  
    /** 
     * 返回消息类型：图文 
     */  
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";  
  
    /** 
     * 请求消息类型：文本 
     */  
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 请求消息类型：图片 
     */  
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";  
  
    /** 
     * 请求消息类型：链接 
     */  
    public static final String REQ_MESSAGE_TYPE_LINK = "link";  
  
    /** 
     * 请求消息类型：地理位置 
     */  
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";  
  
    /** 
     * 请求消息类型：音频 
     */  
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";  
  
    /** 
     * 请求消息类型：推送 
     */  
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";  
  
    /** 
     * 事件类型：subscribe(订阅) 
     */  
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";  
  
    /** 
     * 事件类型：unsubscribe(取消订阅) 
     */  
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";  
  
    /** 
     * 事件类型：CLICK(自定义菜单点击事件) 
     */  
    public static final String EVENT_TYPE_CLICK = "CLICK";  
    
    /** 
     * 事件类型：CLICK(自定义菜单点击事件) 
     */  
    public static final String EVENT_TYPE_VIEW = "VIEW";
    
    /** 
     * 解析微信发来的请求（XML） 
     *  
     * @param request 
     * @return 
     * @throws Exception 
     */  
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {  
        // 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>();  
  
        // 从request中取得输入流  
        InputStream inputStream = request.getInputStream();
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder( );
        String line = null;
        while ((line = bufferReader.readLine()) != null) {  
        	builder.append(line);
        }
        
        StringReader read = new StringReader(builder.toString());
		InputSource source = new InputSource(read);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser;
        
		try {
			parser = factory.newDocumentBuilder();
			Document document = parser.parse(source);
			NodeList nl = document.getElementsByTagName("FromUserName");
			String tagNameContent = nl.item(0).getTextContent();
			map.put("FromUserName", tagNameContent);
			nl = document.getElementsByTagName("ToUserName");
			tagNameContent = nl.item(0).getTextContent();
			map.put("ToUserName", tagNameContent);
			nl = document.getElementsByTagName("MsgType");
			tagNameContent = nl.item(0).getTextContent();
			map.put("MsgType", tagNameContent);
			nl = document.getElementsByTagName("Content");
			if(nl.item(0) != null){
				tagNameContent = nl.item(0).getTextContent();
				logger.info("In util content = " + tagNameContent);
				map.put("Content", tagNameContent);
			}
			nl = document.getElementsByTagName("CreateTime");
			tagNameContent = nl.item(0).getTextContent();
			map.put("CreateTime", tagNameContent);
			nl = document.getElementsByTagName("Event");
			if(nl.item(0) != null){
				tagNameContent = nl.item(0).getTextContent();
				map.put("Event", tagNameContent);
			}
			nl = document.getElementsByTagName("EventKey");
			if(nl.item(0) != null){
				tagNameContent = nl.item(0).getTextContent();
				map.put("EventKey", tagNameContent);
			}
			nl = document.getElementsByTagName("Ticket");
			if(nl.item(0) != null){
				tagNameContent = nl.item(0).getTextContent();
				map.put("Ticket", tagNameContent);
			}
		} catch (ParserConfigurationException e) {
			logger.error(e.getMessage());
		} catch (SAXException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}finally{
			// 释放资源  
	        inputStream.close();  
	        inputStream = null;
		}
        return map;  
    }
    
    
    /**
     * 基本消息对象转换成xml
     * @param baseMessage
     * @return
     */
    public static String baseMessageToXml(BaseMessage baseMessage) {
    	xstream.alias("xml", baseMessage.getClass());  
        return xstream.toXML(baseMessage);
    }
    
    /** 
     * 文本消息对象转换成xml 
     *  
     * @param textMessage 文本消息对象 
     * @return xml 
     */  
    public static String textMessageToXml(TextMessage textMessage) {  
        xstream.alias("xml", textMessage.getClass());  
        return xstream.toXML(textMessage);  
    }
    
    /** 
     * 图文消息对象转换成xml 
     *  
     * @param newsMessage 图文消息对象 
     * @return xml 
     */  
    public static String newsMessageToXml(NewsMessage newsMessage) {  
        xstream.alias("xml", newsMessage.getClass());  
        xstream.alias("item", Article.class);  
        return xstream.toXML(newsMessage);  
    }  
    
    /** 
     * 扩展xstream，使其支持CDATA块 
     */  
    private static XStream xstream = new XStream(new XppDriver() {  
        public HierarchicalStreamWriter createWriter(Writer out) {  
            return new PrettyPrintWriter(out) {  
                // 对所有xml节点的转换都增加CDATA标记  
                boolean cdata = true;  
  
                public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {  
                    super.startNode(name, clazz);  
                }  
  
                protected void writeText(QuickWriter writer, String text) {  
                    if (cdata) {  
                        writer.write("<![CDATA[");  
                        writer.write(text);  
                        writer.write("]]>");  
                    } else {  
                        writer.write(text);  
                    }  
                }  
            };  
        }  
    });
}  