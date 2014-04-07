/**
 * 
 */
package co.speedar.wechat.service.impl;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.speedar.wechat.constant.GuessNumberState;
import co.speedar.wechat.constant.WechatSessionKey;
import co.speedar.wechat.exception.SessionTimeoutException;
import co.speedar.wechat.exception.SpeedarException;
import co.speedar.wechat.service.IGuessNumberService;
import co.speedar.wechat.util.WechatSession;
import co.speedar.wechat.util.WechatSessionContainer;

/**
 * IGuessNumberService's implementation.
 * 
 * @author ben
 * @creation 2014年4月6日
 */
@Component("guessNumberService")
public class GuessNumberServiceImpl implements IGuessNumberService {
	protected static final Logger log = Logger
			.getLogger(GuessNumberServiceImpl.class);

	@Autowired
	private WechatSessionContainer sessionContainer;

	/* (non-Javadoc)
	 * @see co.speedar.wechat.service.IGuessNumberService#generateGuessNumber(java.lang.String)
	 */
	@Override
	public int generateGuessNumber(String openid) {
		int guessNumber = 1 + new Random(System.currentTimeMillis())
				.nextInt(100);// [1, 100]
		WechatSession session = sessionContainer.getSession(openid);
		session.setAttribute(WechatSessionKey.GUESS_NUMBER, guessNumber);
		sessionContainer.setSession(openid, session);
		return guessNumber;
	}

	/* (non-Javadoc)
	 * @see co.speedar.wechat.service.IGuessNumberService#guess(java.lang.String, int)
	 */
	@Override
	public int guess(String openid, int inputNumber) throws SpeedarException {
		WechatSession session = sessionContainer.getSession(openid);
		try {
			int resultNumber = (Integer) session
					.getAttribute(WechatSessionKey.GUESS_NUMBER);
			int guessResult;
			if (inputNumber > resultNumber) {
				guessResult = GuessNumberState.TOO_BIG;
			} else if (inputNumber < resultNumber) {
				guessResult = GuessNumberState.TOO_SMALL;
			} else {
				guessResult = GuessNumberState.BINGO;
			}
			return guessResult;
		} catch (NullPointerException e) {
			log.error(e, e);
			throw new SessionTimeoutException("Session: " + openid
					+ " has timeout.");
		}
	}

}
