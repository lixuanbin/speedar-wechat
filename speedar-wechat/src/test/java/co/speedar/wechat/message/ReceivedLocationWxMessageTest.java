/**
 * 
 */
package co.speedar.wechat.message;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.speedar.wechat.exception.UnsupportedMessageTypeException;
import co.speedar.wechat.message.base.BaseReceivedMessage;

/**
 * @author lixuanbin
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:testContext.xml" })
public class ReceivedLocationWxMessageTest {
	@Value("${requestLocationXml}")
	private String requestLocationXml;

	@Test
	public void testStaticFromXml() throws UnsupportedMessageTypeException {
		ReceivedLocationMessage locationWxMessage = (ReceivedLocationMessage) BaseReceivedMessage
				.fromXml(requestLocationXml);
		assertTrue(
				"MsgType should be location",
				StringUtils.equalsIgnoreCase("location",
						locationWxMessage.getMsgType()));
		assertNotNull("Should load from xml and set Label propertiy.",
				locationWxMessage.getLabel());
		assertNotNull("Should load from xml and set FromUserName propertiy.",
				locationWxMessage.getFromUserName());
		assertNotNull("Should load from xml and set ToUserName propertiy.",
				locationWxMessage.getToUserName());
	}
}
