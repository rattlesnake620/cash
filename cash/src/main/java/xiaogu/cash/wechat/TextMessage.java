package xiaogu.cash.wechat;  

import xiaogu.cash.util.MessageUtil;
  
/** 
 * 文本消息 
 */  
public class TextMessage extends BaseMessage {  
    // 回复的消息内容  
    private String Content;  
    
  
    public TextMessage() {
		super();
		super.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
	}

	public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
}  