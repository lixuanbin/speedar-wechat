/**
 * 
 */
package co.speedar.wechat.controller.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.speedar.wechat.controller.IWechatController;
import co.speedar.wechat.exception.SpeedarException;
import co.speedar.wechat.message.ReceivedEventMessage;
import co.speedar.wechat.message.ResponsedTextMessage;
import co.speedar.wechat.message.base.BaseReceivedMessage;
import co.speedar.wechat.util.ControllerHelper;

/**
 * Event messages handler.
 * 
 * @author ben
 * @creation 2014年4月5日
 */
@Component("eventWechatController")
public class EventWechatController implements IWechatController {
	protected static final Logger log = Logger
			.getLogger(EventWechatController.class);

	@Autowired
	private ControllerHelper helper;

	@Override
	public String handleRequest(String requestXmlString)
			throws SpeedarException {
		BaseReceivedMessage receivedWxMessage;
		receivedWxMessage = BaseReceivedMessage.fromXml(requestXmlString);
		ReceivedEventMessage eventWxMessage = (ReceivedEventMessage) receivedWxMessage;
		String event = eventWxMessage.getEvent();
		// String eventKey = eventWxMessage.getEventKey();
		String content = null;
		ResponsedTextMessage responsedTextWxMessage = new ResponsedTextMessage(
				receivedWxMessage);
		if (StringUtils.equalsIgnoreCase("click", event)) {
			// Click on the menu
			// Do nothing, coz clickable menu is not supportted yet.
		} else if (StringUtils.equalsIgnoreCase("subscribe", event)) {
			// User subscribes, display welcome message.
			log.info("user: " + eventWxMessage.getFromUserName() + "subscribe");
			String messageCode = "subscribe.welcome.message";
			content = helper.getI18NMessage(eventWxMessage.getFromUserName(),
					messageCode, null);
			responsedTextWxMessage.setContent(content);
			return responsedTextWxMessage.toXml();
		} else if (StringUtils.equalsIgnoreCase("unsubscribe", event)) {
			// User unsubscribes.
			log.info("user: " + eventWxMessage.getFromUserName()
					+ "unsubscribe");
		} else {
			// New event.
			log.error("new event message: " + requestXmlString);
		}
		return null;
	}
}
