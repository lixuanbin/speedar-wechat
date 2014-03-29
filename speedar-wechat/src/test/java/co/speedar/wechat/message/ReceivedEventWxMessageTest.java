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
public class ReceivedEventWxMessageTest {
	@Value("${requestEventXml}")
	private String requestEventXml;

	@Test
	public void testStaticFromXml() throws UnsupportedMessageTypeException {
		ReceivedEventMessage eventWxMessage = (ReceivedEventMessage) BaseReceivedMessage
				.fromXml(requestEventXml);
		assertTrue(
				"MsgType should be event",
				StringUtils.equalsIgnoreCase("event",
						eventWxMessage.getMsgType()));
		assertNotNull("Should load from xml and set Event propertiy.",
				eventWxMessage.getEvent());
		assertNotNull("Should load from xml and set FromUserName propertiy.",
				eventWxMessage.getFromUserName());
		assertNotNull("Should load from xml and set ToUserName propertiy.",
				eventWxMessage.getToUserName());
	}
}
