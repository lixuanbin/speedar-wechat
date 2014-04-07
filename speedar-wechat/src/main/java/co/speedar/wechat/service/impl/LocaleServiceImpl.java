/**
 * 
 */
package co.speedar.wechat.service.impl;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import co.speedar.wechat.constant.WechatSessionKey;
import co.speedar.wechat.exception.SpeedarException;
import co.speedar.wechat.exception.UnsupportedLocaleException;
import co.speedar.wechat.service.ILocaleService;
import co.speedar.wechat.util.WechatSession;
import co.speedar.wechat.util.WechatSessionContainer;

/**
 * @author ben
 * @creation 2014年4月6日
 */
@Component("localeService")
public class LocaleServiceImpl implements ILocaleService {
	protected static final Logger log = Logger
			.getLogger(LocaleServiceImpl.class);

	@Autowired
	private WechatSessionContainer sessionContainer;

	@Value("${supported.languages}")
	private String supportedLanguages;

	@Value("${defaultLanguage}")
	private String defaultLanguage;

	/* (non-Javadoc)
	 * @see co.speedar.wechat.service.ILocaleService#getLocale(java.lang.String)
	 */
	@Override
	public Locale getLocale(String openid) throws SpeedarException {
		WechatSession session = sessionContainer.getSession(openid);
		Locale locale;
		Object temp = session.getAttribute(WechatSessionKey.LOCALE);
		if (temp == null) {
			locale = new Locale(defaultLanguage);
		} else {
			locale = (Locale) session.getAttribute(WechatSessionKey.LOCALE);
		}
		return locale;
	}

	/* (non-Javadoc)
	 * @see co.speedar.wechat.service.ILocaleService#changLocale(java.lang.String, java.lang.String)
	 */
	@Override
	public void changLocale(String openid, String language)
			throws UnsupportedLocaleException {
		boolean isSupportedLanguage = false;
		String[] languages = StringUtils.split(supportedLanguages, ",");
		for (String tempLanguage : languages) {
			if (StringUtils.equalsIgnoreCase(tempLanguage, language)) {
				isSupportedLanguage = true;
				break;
			}
		}
		if (!isSupportedLanguage) {
			throw new UnsupportedLocaleException("Language " + language
					+ " is unsupported in this system.");
		}
		// Change and cache locale.
		Locale locale = new Locale(language);
		WechatSession session = sessionContainer.getSession(openid);
		session.setAttribute(WechatSessionKey.LOCALE, locale);
		sessionContainer.setSession(openid, session);
	}

}
