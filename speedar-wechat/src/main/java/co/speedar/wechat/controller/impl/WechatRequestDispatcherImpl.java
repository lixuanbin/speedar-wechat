/**
 * 
 */
package co.speedar.wechat.controller.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.speedar.wechat.constant.BusinessType;
import co.speedar.wechat.constant.MenuState;
import co.speedar.wechat.constant.WechatSessionKey;
import co.speedar.wechat.controller.IWechatRequestDispatcher;
import co.speedar.wechat.exception.SpeedarException;
import co.speedar.wechat.message.ResponsedTextMessage;
import co.speedar.wechat.util.ControllerHelper;
import co.speedar.wechat.util.ControllerHolder;
import co.speedar.wechat.util.WechatSession;
import co.speedar.wechat.util.WechatSessionContainer;
import co.speedar.wechat.util.XmlTool;

/**
 * @author ben
 * 
 */
@Component("wechatRequestDispatcher")
public class WechatRequestDispatcherImpl implements IWechatRequestDispatcher {
	protected static Logger log = Logger
			.getLogger(WechatRequestDispatcherImpl.class);

	@Autowired
	private XmlTool xmlTool;

	@Autowired
	private MenuWechatController menuWechatController;

	@Autowired
	private EventWechatController eventWechatController;

	@Autowired
	private ControllerHolder controllerHolder;

	@Autowired
	private ControllerHelper helper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.meetwe.front.controller.IWechatRequestDispatcher#dispatch(net.meetwe
	 * .front.domain.WechatSignature, java.lang.String)
	 */
	@Override
	public String dispatch(String requestXmlString) {
		// Set signature
		String openId = xmlTool.getValueByTagName(requestXmlString,
				"FromUserName");
		String requestMsgType = xmlTool.getValueByTagName(requestXmlString,
				"MsgType");
		String responsedXmlString = null;
		WechatSessionContainer sessionContainer = helper.getSessionContainer();
		WechatSession session = sessionContainer.getSession(openId);

		// Get|set the business type from|to user session
		int businessType;
		Object temp = session.getAttribute(WechatSessionKey.BUSINESS_TYPE);
		if (temp == null) {
			// Session time out or first request, set menu as the default business type.
			businessType = BusinessType.MENU;
			session.setAttribute(WechatSessionKey.BUSINESS_TYPE, businessType);
			session.setAttribute(WechatSessionKey.BUSINESS_STATE,
					MenuState.INIT);
			sessionContainer.setSession(openId, session);
		} else {
			// Retrieve business type from session.
			businessType = (Integer) temp;

		}
		// Other valid message type besides text at this step.
		temp = session.getAttribute(WechatSessionKey.VALID_MESSAGE_TYPE);
		String validMsgType = (temp != null ? (String) temp : null);
		// Dispatch requests by message type and business type.
		try {
			if (StringUtils.equalsIgnoreCase("text", requestMsgType)
					|| StringUtils.equalsIgnoreCase(validMsgType,
							requestMsgType)) {
				if (StringUtils.equalsIgnoreCase("text", requestMsgType)) {
					// Deal with text message
					String Content = StringUtils.trim(xmlTool
							.getValueByTagName(requestXmlString, "Content"));
					if (StringUtils.equals("?", Content)
							|| StringUtils.equals("？", Content)
							|| StringUtils.equals("#", Content)) {
						// Deal with menu request.
						businessType = BusinessType.MENU;
						session.setAttribute(WechatSessionKey.BUSINESS_TYPE,
								businessType);
						session.setAttribute(WechatSessionKey.BUSINESS_STATE,
								MenuState.INIT);
						sessionContainer.setSession(openId, session);
					}
				}
				if (businessType == BusinessType.MENU) {
					// Handle the menu request.
					responsedXmlString = menuWechatController
							.handleRequest(requestXmlString);
				} else {
					// Other requests, dispatch to an appropriate controller to handle it.
					responsedXmlString = controllerHolder.getController(
							businessType).handleRequest(requestXmlString);
				}
			} else if (StringUtils.equalsIgnoreCase("event", requestMsgType)) {
				// Deal with event message.
				responsedXmlString = eventWechatController
						.handleRequest(requestXmlString);
			} else {
				// Invalid message type.
				log.error("Invalid message type: \n" + requestXmlString);
				throw new SpeedarException("invalid.message.type",
						"Invalid message type: " + requestMsgType);
			}
		} catch (SpeedarException e) {
			// Handle all controller exceptions here.
			log.error(e, e);
			String code = e.getCode();
			String responseTextContent = helper.getI18NMessage(openId, code,
					null);
			ResponsedTextMessage responsedWxMessage = helper
					.generateResponseTextWxMessage(requestXmlString,
							responseTextContent);
			responsedXmlString = responsedWxMessage.toXml();
		}
		return responsedXmlString;
	}

}
