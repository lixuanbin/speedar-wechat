/**
 * 
 */
package co.speedar.wechat.message;

import co.speedar.wechat.message.base.BaseReceivedMessage;

/**
 * @author lixuanbin@csair.com
 * @creation 2012-11-21
 */
public class ReceivedLocationMessage extends BaseReceivedMessage {
	private float Location_X;
	private float Location_Y;
	private float Scale;
	private String Label;

	private String MsgId;

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	public float getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(float location_X) {
		this.Location_X = location_X;
	}

	public float getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(float location_Y) {
		this.Location_Y = location_Y;
	}

	public float getScale() {
		return Scale;
	}

	public void setScale(float scale) {
		this.Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		this.Label = label;
	}

}
