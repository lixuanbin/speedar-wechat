/**
 * 
 */
package co.speedar.wechat.service;

import static org.junit.Assert.*;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ben
 * @creation 2014年4月2日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:testContext.xml",
		"classpath:speedarWechatContext.xml" })
public class CacheClientTest {
	@Autowired
	private ICacheClient cacheClient;

	@Test
	public void testSetAndGetCacheValue() {
		String testString = "This is a test string.";
		boolean setResult = cacheClient.setCacheValue("testKey", testString);
		assertTrue("Should set ok.", setResult);
		String cachedString = (String) cacheClient.getCacheValue("testKey");
		assertTrue("Should be equal.",
				StringUtils.equals(testString, cachedString));
	}
}
