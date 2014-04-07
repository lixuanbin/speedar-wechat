/**
 * 
 */
package co.speedar.wechat.util;

import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import co.speedar.wechat.constant.WechatSessionKey;
import co.speedar.wechat.exception.UnsupportedLocaleException;
import co.speedar.wechat.message.ResponsedTextMessage;
import co.speedar.wechat.message.base.BaseReceivedMessage;

/**
 * Contains some useful tools and data that might be helpful to wechat controllers.
 * 
 * @author ben
 * @creation 2014年4月5日
 */
@Component("controllerHelper")
public class ControllerHelper {
	@Autowired
	private WechatSessionContainer sessionContainer;

	@Autowired
	private SpeedarWechatCredential credential;

	@Value("${context.path}")
	private String contextPath;

	@Value("${defaultLanguage}")
	private String defaultLanguage;

	@Value("${supported.languages}")
	private String supportedLanguages;

	@Autowired
	@Qualifier("messageSource2")
	private ResourceBundleMessageSource messageSource2;

	/**
	 * @return
	 */
	public WechatSessionContainer getSessionContainer() {
		return sessionContainer;
	}

	/**
	 * @return
	 */
	public SpeedarWechatCredential getCredential() {
		return credential;
	}

	/**
	 * @return
	 */
	public String getDomainName() {
		return credential.getDomainName();
	}

	/**
	 * @return
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * @return
	 */
	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	/**
	 * Get supported languages.<br/>
	 * eg: en_US.
	 * 
	 * @return
	 */
	public String[] getSupportedLanguages() {
		String[] languages = StringUtils.split(supportedLanguages, ",");
		return languages;
	}

	/**
	 * @return
	 */
	public ResourceBundleMessageSource getMessageSource() {
		return messageSource2;
	}

	/**
	 * Get locale from cache, set to default locale if not set.
	 * 
	 * @param openid
	 * @return
	 */
	public Locale getLocale(String openid) {
		Locale locale;
		WechatSession session;
		session = sessionContainer.getSession(openid);
		Object temp = session.getAttribute(WechatSessionKey.LOCALE);
		if (temp != null) {
			locale = (Locale) temp;
		} else {
			locale = new Locale(defaultLanguage);
			session.setAttribute(WechatSessionKey.LOCALE, locale);
			sessionContainer.setSession(openid, session);
		}
		return locale;
	}

	/**
	 * Change the user's locale.
	 * 
	 * @param openid
	 * @param language
	 * @throws UnsupportedLocaleException 
	 */
	public void changeLocale(String openid, String language)
			throws UnsupportedLocaleException {
		// Check if language is supported.
		boolean isSupportedLanguage = false;
		String[] languages = getSupportedLanguages();
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

	/**
	 * Generate response text message by received message and assigned text
	 * 
	 * @param receivedWxMessage
	 * @param responseTextContent
	 * @return
	 */
	public ResponsedTextMessage generateResponseTextWxMessage(
			BaseReceivedMessage receivedWxMessage, String responseTextContent) {
		ResponsedTextMessage textMessage = new ResponsedTextMessage(
				receivedWxMessage);
		textMessage.setContent(responseTextContent);
		return textMessage;
	}

	/**
	 * Generate response text message by received message and assigned text
	 * 
	 * @param requestXmlString
	 * @param responseTextContent
	 * @return
	 */
	public ResponsedTextMessage generateResponseTextWxMessage(
			String requestXmlString, String responseTextContent) {
		String FromUserName = XmlTool.getValueByTag(requestXmlString,
				"FromUserName");
		String ToUserName = XmlTool.getValueByTag(requestXmlString,
				"ToUserName");
		ResponsedTextMessage responsedTextWxMessage = new ResponsedTextMessage();
		responsedTextWxMessage.setCreateTime(new Date());
		responsedTextWxMessage.setFromUserName(ToUserName);
		responsedTextWxMessage.setMsgType("text");
		responsedTextWxMessage.setToUserName(FromUserName);
		responsedTextWxMessage.setContent(responseTextContent);
		responsedTextWxMessage.setFuncFlag("0");
		return responsedTextWxMessage;
	}

	/**
	 * Get i18n string message for a specific user, using the locale cached in the session container.
	 * 
	 * @param openid the openid of the user
	 * @param messageCode the code to lookup up, such as 'calculator.noRateSet'
	 * @param args Array of arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), or null if none.
	 * @return
	 */
	public String getI18NMessage(String openid, String messageCode,
			Object[] args) {
		Locale locale = getLocale(openid);
		String message = messageSource2.getMessage(messageCode, args, locale);
		return message;
	}
}
