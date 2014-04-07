/**
 * 
 */
package co.speedar.wechat.controller.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.speedar.wechat.constant.GuessNumberState;
import co.speedar.wechat.constant.WechatSessionKey;
import co.speedar.wechat.controller.BaseWechatController;
import co.speedar.wechat.exception.SpeedarException;
import co.speedar.wechat.message.ReceivedTextMessage;
import co.speedar.wechat.message.ResponsedTextMessage;
import co.speedar.wechat.message.base.BaseReceivedMessage;
import co.speedar.wechat.message.base.BaseResponsedMessage;
import co.speedar.wechat.service.IGuessNumberService;
import co.speedar.wechat.util.ControllerHelper;
import co.speedar.wechat.util.WechatSession;
import co.speedar.wechat.util.WechatSessionContainer;

/**
 * Guess number game wechat controller.
 * 
 * @author ben
 * @creation 2014年4月6日
 */
@Component("guessNumberWechatController")
public class GuessNumberWechatController extends BaseWechatController {
	protected static Logger log = Logger
			.getLogger(GuessNumberWechatController.class);

	@Autowired
	private IGuessNumberService guessNumberService;

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
			if (!StringUtils.isNumeric(receivedTextContent)
					&& businessState != GuessNumberState.INIT) {
				// Invalid user input, must be integer.
				throw new SpeedarException("must.be.integer",
						"invalid user input: " + receivedTextContent);
			}
			switch (businessState) {
			case GuessNumberState.INIT:
				messageCode = "guess.number.prompt";
				prompt = helper.getI18NMessage(openid, messageCode, null);
				messageCode = "go.back.prompt";
				prompt += helper.getI18NMessage(openid, messageCode, null);
				responsedTextMessage.setContent(prompt);
				int number = guessNumberService.generateGuessNumber(openid);
				log.info("Guess number: " + number + " generated for user: "
						+ openid);
				session.setAttribute(WechatSessionKey.GUESS_NUMBER, number);
				session.setAttribute(WechatSessionKey.BUSINESS_STATE,
						GuessNumberState.PROMPT_SENT);
				container.setSession(openid, session);
				break;
			case GuessNumberState.PROMPT_SENT:
			case GuessNumberState.TOO_BIG:
			case GuessNumberState.TOO_SMALL:
				int guessResult = guessNumberService.guess(openid,
						Integer.valueOf(receivedTextContent));
				session = container.getSession(openid);
				session.setAttribute(WechatSessionKey.BUSINESS_STATE,
						guessResult);
				container.setSession(openid, session);
				if (guessResult == GuessNumberState.TOO_BIG) {
					// Prompt for too big.
					messageCode = "guess.number.big";
					prompt = helper.getI18NMessage(openid, messageCode, null);
					messageCode = "go.back.prompt";
					prompt += helper.getI18NMessage(openid, messageCode, null);
				} else if (guessResult == GuessNumberState.TOO_SMALL) {
					// Prompt for too small.
					messageCode = "guess.number.small";
					prompt = helper.getI18NMessage(openid, messageCode, null);
					messageCode = "go.back.prompt";
					prompt += helper.getI18NMessage(openid, messageCode, null);
				} else {
					// Congratulations for bingo.
					messageCode = "guess.number.bingo";
					prompt = helper.getI18NMessage(openid, messageCode, null);
					messageCode = "go.back.prompt";
					prompt += helper.getI18NMessage(openid, messageCode, null);
					session.setAttribute(WechatSessionKey.BUSINESS_STATE,
							GuessNumberState.INIT);
					container.setSession(openid, session);
				}
				responsedTextMessage.setContent(prompt);
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
		return responsedMessage;
	}

}
