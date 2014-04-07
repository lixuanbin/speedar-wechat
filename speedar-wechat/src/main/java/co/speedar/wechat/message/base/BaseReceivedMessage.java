/**
 * 
 */
package co.speedar.wechat.message.base;

import org.apache.commons.lang.StringUtils;

import co.speedar.wechat.exception.UnsupportedMessageTypeException;
import co.speedar.wechat.message.ReceivedEventMessage;
import co.speedar.wechat.message.ReceivedImageMessage;
import co.speedar.wechat.message.ReceivedLocationMessage;
import co.speedar.wechat.message.ReceivedTextMessage;
import co.speedar.wechat.message.ReceivedVoiceMessage;
import co.speedar.wechat.util.MessageDateConverter;
import co.speedar.wechat.util.XmlTool;

import com.thoughtworks.xstream.XStream;

/**
 * @author lixuanbin
 * @creation 2012-11-21
 */
public abstract class BaseReceivedMessage extends BaseMessage {
	public BaseReceivedMessage() {
	}

	/**
	 * Construct an instance of BaseReceivedMessage from the given xml string.
	 * 
	 * @param xmlString
	 * @return
	 * @throws UnsupportedMessageTypeException
	 */
	public static BaseReceivedMessage fromXml(String xmlString)
			throws UnsupportedMessageTypeException {
		BaseReceivedMessage receivedWxMessage = null;
		String MsgType = XmlTool.getValueByTag(xmlString, "MsgType");
		// XO mapping.
		XStream xstream = new XStream();
		MessageDateConverter converter = new MessageDateConverter();
		xstream.registerConverter(converter);
		if (StringUtils.equalsIgnoreCase(MsgType, "text")) {
			xstream.alias("xml", ReceivedTextMessage.class);
		} else if (StringUtils.equalsIgnoreCase(MsgType, "event")) {
			xstream.alias("xml", ReceivedEventMessage.class);
		} else if (StringUtils.equalsIgnoreCase(MsgType, "location")) {
			xstream.alias("xml", ReceivedLocationMessage.class);
		} else if (StringUtils.equalsIgnoreCase(MsgType, "image")) {
			xstream.alias("xml", ReceivedImageMessage.class);
		} else if (StringUtils.equalsIgnoreCase(MsgType, "voice")) {
			xstream.alias("xml", ReceivedVoiceMessage.class);
		} else {
			throw new UnsupportedMessageTypeException(
					"Unsupported message type " + MsgType + " in \n"
							+ xmlString);
		}
		receivedWxMessage = (BaseReceivedMessage) xstream.fromXML(xmlString);
		xstream = null;
		return receivedWxMessage;
	}
}
