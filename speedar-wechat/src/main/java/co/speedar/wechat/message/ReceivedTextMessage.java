/**
 * 
 */
package co.speedar.wechat.message;

import co.speedar.wechat.message.base.BaseReceivedMessage;

/**
 * @author lixuanbin
 * @creation 2012-11-21
 */
public class ReceivedTextMessage extends BaseReceivedMessage {
	private String Content;

	private String MsgId;

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
