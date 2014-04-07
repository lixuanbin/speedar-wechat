/**
 * 
 */
package co.speedar.wechat.controller.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import co.speedar.wechat.constant.EchoState;
import co.speedar.wechat.constant.WechatSessionKey;
import co.speedar.wechat.controller.BaseWechatController;
import co.speedar.wechat.exception.SpeedarException;
import co.speedar.wechat.message.ReceivedTextMessage;
import co.speedar.wechat.message.ResponsedTextMessage;
import co.speedar.wechat.message.base.BaseReceivedMessage;
import co.speedar.wechat.message.base.BaseResponsedMessage;
import co.speedar.wechat.util.ControllerHelper;
import co.speedar.wechat.util.WechatSession;
import co.speedar.wechat.util.WechatSessionContainer;

/**
 * Echo wechat controller.
 * 
 * @author ben
 * @creation 2014年4月6日
 */
@Component("echoWechatController")
public class EchoWechatController extends BaseWechatController {
	protected static Logger log = Logger.getLogger(EchoWechatController.class);

	/* (non-Javadoc)
	 * @see co.speedar.wechat.controller.BaseWechatController#doProcess(co.speedar.wechat.message.base.BaseReceivedMessage)
	 */
	@Override
	public BaseResponsedMessage doProcess(BaseReceivedMessage receivedWxMessage)
			throws SpeedarException {
		ControllerHelper helper = getHelper();
		WechatSessionContainer container = helper.getSessionContainer();
		String openid = receivedWxMessage.getFromUserName();
		WechatSession session = container.getSession(openid);
		int businessState = (Integer) session
				.getAttribute(WechatSessionKey.BUSINESS_STATE);
		BaseResponsedMessage responsedMessage;
		ResponsedTextMessage responsedTextMessage = new ResponsedTextMessage(
				receivedWxMessage);
		String MsgType = receivedWxMessage.getMsgType();
		String messageCode;
		String prompt;
		if (StringUtils.equalsIgnoreCase(MsgType, "text")) {
			// Only text message is supported.
			ReceivedTextMessage receivedTextMessage = (ReceivedTextMessage) receivedWxMessage;
			String receivedTextContent = receivedTextMessage.getContent();
			switch (businessState) {
			case EchoState.INIT:
				messageCode = "echo.prompt";
				prompt = helper.getI18NMessage(openid, messageCode, null);
				messageCode = "go.back.prompt";
				prompt += helper.getI18NMessage(openid, messageCode, null);
				responsedTextMessage.setContent(prompt);
				session.setAttribute(WechatSessionKey.BUSINESS_STATE,
						EchoState.ECHO);
				break;
			case EchoState.ECHO:
				responsedTextMessage.setContent(receivedTextContent);
				break;
			default:
				// State transition error.
				messageCode = "state.transition.error";
				throw new SpeedarException(messageCode,
						"State transition error: " + businessState);
			}
		} else {
			// Invalid message type.
			log.error("Invalid message type: \n" + MsgType);
			throw new SpeedarException("invalid.message.type",
					"Invalid message type: " + MsgType);
		}
		responsedMessage = responsedTextMessage;
		container.setSession(openid, session);
		return responsedMessage;
	}

}
