/**
 * 
 */
package co.speedar.wechat.service;

import co.speedar.wechat.exception.SpeedarException;

/**
 * Service interfaces for guess number business.
 * 
 * @author ben
 * @creation 2014年4月6日
 */
public interface IGuessNumberService {
	/**
	 * Generate a random integer(less than 100) for a user and put it into session.
	 * 
	 * @param openid
	 * @return
	 */
	public int generateGuessNumber(String openid);

	/**
	 * User guesses number. Compare it with the answer cached in the session.
	 * 
	 * @param openid
	 * @param inputNumber
	 * @return GuessNumberState.TOO_BIG | GuessNumberState.TOO_SMALL | GuessNumberState.BINGO
	 */
	public int guess(String openid, int inputNumber) throws SpeedarException;
}
