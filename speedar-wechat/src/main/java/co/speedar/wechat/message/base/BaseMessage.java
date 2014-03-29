/**
 * 
 */
package co.speedar.wechat.message.base;

import java.util.Date;

/**
 * @author lixuanbin
 * @creation 2012-11-21
 */
public class BaseMessage {
	private String ToUserName;
	private String FromUserName;
	private Date CreateTime;
	private String MsgType;

	public BaseMessage() {

	}

	public BaseMessage(BaseMessage baseWxMessage) {
		this.FromUserName = baseWxMessage.getFromUserName();
		this.ToUserName = baseWxMessage.getToUserName();
		this.CreateTime = baseWxMessage.getCreateTime();
		this.MsgType = baseWxMessage.getMsgType();
	}

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

}
