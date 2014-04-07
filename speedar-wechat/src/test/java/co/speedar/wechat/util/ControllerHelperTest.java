/**
 * 
 */
package co.speedar.wechat.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ben
 * @creation 2014年4月7日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:testContext.xml",
		"classpath:speedarWechatContext.xml" })
public class ControllerHelperTest {
	@Autowired
	private ControllerHelper helper;

	@Test
	public void testGetI18NMessage() {
		String openid = "fakeopenid";
		String messageCode = "user.menu";
		String userMenu = helper.getI18NMessage(openid, messageCode, null);
		assertNotNull("Should not be null.", userMenu);
	}
}
