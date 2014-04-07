/**
 * 
 */
package co.speedar.wechat.controller.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import co.speedar.wechat.constant.ChangeLocaleState;
import co.speedar.wechat.constant.WechatSessionKey;
import co.speedar.wechat.controller.BaseWechatController;
import co.speedar.wechat.exception.SpeedarException;
import co.speedar.wechat.message.ReceivedTextMessage;
import co.speedar.wechat.message.ResponsedTextMessage;
import co.speedar.wechat.message.base.BaseReceivedMessage;
import co.speedar.wechat.message.base.BaseResponsedMessage;
import co.speedar.wechat.service.ILocaleService;
import co.speedar.wechat.util.ControllerHelper;
import co.speedar.wechat.util.WechatSession;
import co.speedar.wechat.util.WechatSessionContainer;

/**
 * Change locale wechat controller.
 * 
 * @author ben
 * @creation 2014年4月6日
 */
@Component("changeLocaleWechatController")
public class ChangeLocaleWechatController extends BaseWechatController {
	protected static Logger log = Logger
			.getLogger(ChangeLocaleWechatController.class);

	@Autowired
	private ILocaleService localeService;

	@Value("${supported.languages}")
	private String supportedLanguages;

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
		String[] languages = StringUtils.split(supportedLanguages, ",");
		if (StringUtils.equalsIgnoreCase(MsgType, "text")) {
			// Only text message is supported.
			ReceivedTextMessage receivedTextMessage = (ReceivedTextMessage) receivedWxMessage;
			String receivedTextContent = receivedTextMessage.getContent();
			if (!StringUtils.isNumeric(receivedTextContent)
					&& businessState != ChangeLocaleState.INIT) {
				throw new SpeedarException("must.be.integer", "User: " + openid
						+ " input: " + receivedTextContent + " is invalid.");
			}
			switch (businessState) {
			case ChangeLocaleState.INIT:
				messageCode = "change.locale.prompt";
				prompt = helper.getI18NMessage(openid, messageCode, null);
				messageCode = "go.back.prompt";
				prompt += helper.getI18NMessage(openid, messageCode, null);
				responsedTextMessage.setContent(prompt);
				session.setAttribute(WechatSessionKey.BUSINESS_STATE,
						ChangeLocaleState.PROMPT_SENT);
				break;
			case ChangeLocaleState.PROMPT_SENT:
				int option = Integer.valueOf((String) receivedTextContent);
				if (option < 0 || option > languages.length) {
					throw new SpeedarException("invalid.menu.option",
							"Invalid language option: " + option + ", user: "
									+ openid);
				}
				String language = languages[option - 1];
				localeService.changLocale(openid, language);
				messageCode = "change.locale.success";
				prompt = helper.getI18NMessage(openid, messageCode, null);
				messageCode = "go.back.prompt";
				prompt += helper.getI18NMessage(openid, messageCode, null);
				responsedTextMessage.setContent(prompt);
				session.setAttribute(WechatSessionKey.BUSINESS_STATE,
						ChangeLocaleState.INIT);
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
