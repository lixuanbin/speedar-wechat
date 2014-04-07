/**
 * 
 */
package co.speedar.wechat.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.speedar.wechat.constant.GuessNumberState;
import co.speedar.wechat.constant.WechatSessionKey;
import co.speedar.wechat.exception.SpeedarException;
import co.speedar.wechat.util.WechatSession;
import co.speedar.wechat.util.WechatSessionContainer;

/**
 * @author ben
 * @creation 2014年4月6日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:testContext.xml",
		"classpath:speedarWechatContext.xml" })
public class GuessNumberServiceTest {
	@Autowired
	private IGuessNumberService guessNumberService;

	@Autowired
	private WechatSessionContainer sessionContainer;

	private String openid = "fakeopenid";

	@Test
	public void testGenerateGuessNumber() {
		int number = guessNumberService.generateGuessNumber(openid);
		assertTrue("Should be smaller than 100 and bigger than 0.",
				(0 < number && number <= 100));
	}

	@Test
	public void testGuessNumber() throws SpeedarException {
		int number = 55;
		WechatSession session = sessionContainer.getSession(openid);
		session.setAttribute(WechatSessionKey.GUESS_NUMBER, number);
		sessionContainer.setSession(openid, session);
		int guess = 23;
		int guessResult = guessNumberService.guess(openid, guess);
		assertTrue("Should be too small.",
				GuessNumberState.TOO_SMALL == guessResult);
		guess = 63;
		guessResult = guessNumberService.guess(openid, guess);
		assertTrue("Should be too big.",
				GuessNumberState.TOO_BIG == guessResult);
		guess = 55;
		guessResult = guessNumberService.guess(openid, guess);
		assertTrue("Should be bingo.", GuessNumberState.BINGO == guessResult);
	}
}
