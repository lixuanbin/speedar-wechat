/**
 * 
 */
package co.speedar.wechat.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import co.speedar.wechat.message.ReceivedTextMessage;
import co.speedar.wechat.message.ResponsedTextMessage;
import co.speedar.wechat.message.base.BaseReceivedMessage;
import co.speedar.wechat.message.base.BaseResponsedMessage;
import co.speedar.wechat.service.IEchoService;

/**
 * @author ben
 * @creation 2014年3月27日
 */
@Component("echoService")
public class EchoServiceImpl implements IEchoService {

	/* (non-Javadoc)
	 * Echo back the message, only text message is surported.
	 * @see co.speedar.wechat.service.IEchoService#echo(co.speedar.wechat.message.base.BaseReceivedMessage)
	 */
	@Override
	public BaseResponsedMessage echo(BaseReceivedMessage receivedMessage) {
		ResponsedTextMessage responsedTextMessage = new ResponsedTextMessage(
				receivedMessage);
		String msgType = receivedMessage.getMsgType();
		String content = null;
		if (StringUtils.equalsIgnoreCase("text", msgType)) {
			ReceivedTextMessage receivedTextMessage = (ReceivedTextMessage) receivedMessage;
			content = receivedTextMessage.getContent();
		} else {
			content = "Sorry, only text message is surported.";
		}
		responsedTextMessage.setContent(content);
		return responsedTextMessage;
	}

}
