/**
 * 
 */
package co.speedar.wechat.service;

import java.util.Locale;

import co.speedar.wechat.exception.SpeedarException;
import co.speedar.wechat.exception.UnsupportedLocaleException;

/**
 * Locale related service interfaces.
 * 
 * @author ben
 * @creation 2014年4月6日
 */
public interface ILocaleService {
	/**
	 * Get a specific user's locale from session.
	 * 
	 * @param openid
	 * @return
	 */
	public Locale getLocale(String openid) throws SpeedarException;

	/**
	 * Change a specific user's locale.
	 * 
	 * @param openid
	 * @param language
	 * @throws UnsupportedLocaleException
	 */
	public void changLocale(String openid, String language)
			throws UnsupportedLocaleException;
}
