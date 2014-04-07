/**
 * 
 */
package co.speedar.wechat.service;

import static org.junit.Assert.assertNotNull;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.speedar.wechat.exception.SpeedarException;
import co.speedar.wechat.exception.UnsupportedLocaleException;

/**
 * @author ben
 * @creation 2014年4月7日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:testContext.xml",
		"classpath:speedarWechatContext.xml" })
public class LocaleServiceTest {
	@Autowired
	private ILocaleService localeService;
	private String openid = "fakeopenid";

	@Test
	public void testGetLocale() throws SpeedarException {
		Locale locale = localeService.getLocale(openid);
		assertNotNull("Should not be null.", locale);
	}

	@Test
	public void testChangeLocale() throws UnsupportedLocaleException {
		String language = "en_US";
		localeService.changLocale(openid, language);
	}
}
