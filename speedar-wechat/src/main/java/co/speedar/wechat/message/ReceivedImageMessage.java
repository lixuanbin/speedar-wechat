/**
 * 
 */
package co.speedar.wechat.message;

import co.speedar.wechat.message.base.BaseReceivedMessage;

/**
 * @author lixuanbin
 * @creation 2012-11-21
 */
public class ReceivedImageMessage extends BaseReceivedMessage {
	private String PicUrl;

	private String MsgId;

	private String MediaId;

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String PicUrl) {
		this.PicUrl = PicUrl;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

}
