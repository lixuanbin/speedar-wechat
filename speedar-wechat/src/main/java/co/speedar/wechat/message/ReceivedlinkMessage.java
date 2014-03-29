package co.speedar.wechat.message;

import co.speedar.wechat.message.base.BaseReceivedMessage;

/**
 * @author lixuanbin
 * 
 */
@Deprecated
public class ReceivedlinkMessage extends BaseReceivedMessage {

	private String Title;
	private String Description;
	private String Url;

	private String MsgId;

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String Title) {
		this.Title = Title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		this.Url = url;
	}

}
