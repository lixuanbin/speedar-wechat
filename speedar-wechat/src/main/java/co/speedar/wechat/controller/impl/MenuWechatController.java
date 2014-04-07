/**
 * 
 */
package co.speedar.wechat.controller.impl;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import co.speedar.wechat.constant.MenuState;
import co.speedar.wechat.constant.WechatSessionKey;
import co.speedar.wechat.controller.IWechatController;
import co.speedar.wechat.exception.SpeedarException;
import co.speedar.wechat.message.ReceivedTextMessage;
import co.speedar.wechat.message.ResponsedTextMessage;
import co.speedar.wechat.message.base.BaseReceivedMessage;
import co.speedar.wechat.message.base.BaseResponsedMessage;
import co.speedar.wechat.util.ControllerHelper;
import co.speedar.wechat.util.ControllerHolder;
import co.speedar.wechat.util.WechatSession;
import co.speedar.wechat.util.WechatSessionContainer;

/**
 * @author ben
 * 
 */
@Component("menuWechatController")
public class MenuWechatController implements IWechatController {
	public static final Logger log = Logger
			.getLogger(MenuWechatController.class);

	@Autowired
	@Qualifier("menu2BusinessTypeProperties")
	private Properties menu2BusinessTypeProperties;

	@Autowired
	private ControllerHolder controllerHolder;

	@Autowired
	private ControllerHelper helper;

	/**
	 * @param receivedWxMessage
	 * @return
	 */
	public String doProcess(String requestXml) throws SpeedarException {
		BaseReceivedMessage receivedWxMessage = BaseReceivedMessage
				.fromXml(requestXml);
		BaseResponsedMessage responsedMessage = null;
		ResponsedTextMessage responsedTextMessage = null;
		WechatSessionContainer sessionContainer = helper.getSessionContainer();
		String openid = receivedWxMessage.getFromUserName();
		WechatSession session = sessionContainer.getSession(openid);
		String MsgType = receivedWxMessage.getMsgType();
		String userMenuCode = "user.menu";
		String userMenu = helper.getI18NMessage(openid, userMenuCode, null);
		String invalidMenuOptionCode = "invalid.menu.option";
		String invalidMenuOption = helper.getI18NMessage(openid,
				invalidMenuOptionCode, null);
		if (StringUtils.equalsIgnoreCase(MsgType, "text")) {
			// Handle the text message only.
			ReceivedTextMessage receivedTextMessage = (ReceivedTextMessage) receivedWxMessage;
			String requestTextContent = StringUtils.trim(receivedTextMessage
					.getContent());
			int menuState = (Integer) session
					.getAttribute(WechatSessionKey.BUSINESS_STATE);
			switch (menuState) {
			case MenuState.INIT:
				// Initial state, return menus to the uesr.
				responsedTextMessage = new ResponsedTextMessage(
						receivedWxMessage);
				responsedTextMessage.setMsgType("text");
				if (StringUtils.equals("#", requestTextContent)
						|| StringUtils.equals("＃", requestTextContent)) {
					// TODO Return the admin menu.
				} else {
					// Return the user menu.
					responsedTextMessage.setContent(userMenu);
				}
				responsedMessage = responsedTextMessage;
				responsedTextMessage = null;
				session.setAttribute(WechatSessionKey.BUSINESS_STATE,
						MenuState.MENU_SENT);
				break;
			case MenuState.MENU_SENT:
				// Menu has been sent.
				if (StringUtils.isNumeric(requestTextContent)
						&& menu2BusinessTypeProperties.get(requestTextContent) != null) {
					// Dispatch to the right controller.
					int userSelectType = Integer
							.valueOf((String) menu2BusinessTypeProperties
									.get(receivedTextMessage.getContent()));
					// Set the business type and initiate the state.
					session.setAttribute(WechatSessionKey.BUSINESS_TYPE,
							userSelectType);
					session.setAttribute(WechatSessionKey.BUSINESS_STATE,
							userSelectType * 100);
					sessionContainer.setSession(openid, session);
					// Call a specific controller to handle it.
					return controllerHolder.getController(userSelectType)
							.handleRequest(requestXml);
				} else {
					// Invalid menu option, prompt for user input.
					responsedTextMessage = new ResponsedTextMessage(
							receivedWxMessage);
					responsedTextMessage.setMsgType("text");
					responsedTextMessage.setContent(invalidMenuOption + "\r\n"
							+ userMenu);
					responsedMessage = responsedTextMessage;
					responsedTextMessage = null;
				}
				break;
			default:
				break;
			}

		} else {
			// Invalid message type.
			log.error("Invalid message type: \n" + requestXml);
			throw new SpeedarException("invalid.message.type",
					"Invalid message type: " + MsgType);
		}
		// Set back the session.
		sessionContainer.setSession(openid, session);
		// Return the response xml.
		return (responsedMessage != null ? responsedMessage.toXml() : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.meetwe.front.controller.IWechatController#handleRequest(java.lang
	 * .String)
	 */
	public String handleRequest(String requestXmlString)
			throws SpeedarException {
		String responseXml = null;
		responseXml = doProcess(requestXmlString);
		return responseXml;
	}
}
