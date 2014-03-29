/**
 * 
 */
package co.speedar.wechat.message;

import co.speedar.wechat.message.base.BaseReceivedMessage;
import co.speedar.wechat.message.base.BaseResponsedMessage;

/**
 * @author lixuanbin
 * @creation 2012-11-21
 */
public class ResponsedTextMessage extends BaseResponsedMessage {
	private String Content;
	private String FuncFlag;

	public ResponsedTextMessage() {
	}

	public ResponsedTextMessage(BaseReceivedMessage receivedWxMessage) {
		super(receivedWxMessage);
		this.setMsgType("text");
		this.setFuncFlag("0");
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}

	public String getFuncFlag() {
		return FuncFlag;
	}

	public void setFuncFlag(String funcFlag) {
		this.FuncFlag = funcFlag;
	}

}
