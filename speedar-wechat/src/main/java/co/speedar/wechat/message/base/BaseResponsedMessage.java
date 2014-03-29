/**
 * 
 */
package co.speedar.wechat.message.base;

import java.util.Date;

import co.speedar.wechat.util.MessageDateConverter;

import com.thoughtworks.xstream.XStream;

/**
 * @author lixuanbin
 * @creation 2012-11-21
 */
public class BaseResponsedMessage extends BaseMessage {
	public BaseResponsedMessage() {

	}

	public BaseResponsedMessage(BaseReceivedMessage receivedWxMessage) {
		this();
		setCreateTime(new Date());
		setFromUserName(receivedWxMessage.getToUserName());
		setToUserName(receivedWxMessage.getFromUserName());
	}

	public String toXml() {
		XStream xstream = new XStream();
		MessageDateConverter converter = new MessageDateConverter();
		xstream.registerConverter(converter);
		xstream.alias("xml", this.getClass());
		String xmlString = xstream.toXML(this);
		xstream = null;
		converter = null;
		return xmlString;
	}

	@Override
	public String toString() {
		return toXml();
	}
}
