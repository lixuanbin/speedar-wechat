/**
 * 
 */
package co.speedar.wechat.message;

import co.speedar.wechat.message.base.BaseReceivedMessage;


/**
 * @author lixuanbin
 * @creation 2012-11-21
 */
public class ReceivedEventMessage extends BaseReceivedMessage {
	private String Event;
	private String EventKey;

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

}
