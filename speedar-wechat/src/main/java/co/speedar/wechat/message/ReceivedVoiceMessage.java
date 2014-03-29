/**
 * 
 */
package co.speedar.wechat.message;

import co.speedar.wechat.message.base.BaseReceivedMessage;

/**
 * @author lixuanbin
 * @creation 2012-11-21
 */
public class ReceivedVoiceMessage extends BaseReceivedMessage {
	private String Format;

	private String MsgId;

	private String MediaId;

	private String Recognition;

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		this.MsgId = msgId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String Format) {
		this.Format = Format;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		this.MediaId = mediaId;
	}

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		this.Recognition = recognition;
	}

}
