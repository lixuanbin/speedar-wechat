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
public class ReceivedTextWxMessageTest {
	@Value("${requestTextXml}")
	private String requestTextXml;

	@Test
	public void testStaticFromXml() throws UnsupportedMessageTypeException {
		ReceivedTextMessage textWxMessage = (ReceivedTextMessage) BaseReceivedMessage
				.fromXml(requestTextXml);
		assertTrue("MsgType should be text", StringUtils.equalsIgnoreCase(
				"text", textWxMessage.getMsgType()));
		assertNotNull("Should load from xml and set Content propertiy.",
				textWxMessage.getContent());
		assertNotNull("Should load from xml and set FromUserName propertiy.",
				textWxMessage.getFromUserName());
		assertNotNull("Should load from xml and set ToUserName propertiy.",
				textWxMessage.getToUserName());
	}
}
