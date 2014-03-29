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
public class ReceivedVoiceWxMessageTest {
	@Value("${requestVoiceXml}")
	private String requestVoiceXml;

	@Test
	public void testStaticFromXml() throws UnsupportedMessageTypeException {
		ReceivedVoiceMessage voiceWxMessage = (ReceivedVoiceMessage) BaseReceivedMessage
				.fromXml(requestVoiceXml);
		assertTrue(
				"MsgType should be voice",
				StringUtils.equalsIgnoreCase("voice",
						voiceWxMessage.getMsgType()));
		assertNotNull("Should load from xml and set Format propertiy.",
				voiceWxMessage.getFormat());
		assertNotNull("Should load from xml and set MediaId propertiy.",
				voiceWxMessage.getMediaId());
		assertNotNull("Should load from xml and set FromUserName propertiy.",
				voiceWxMessage.getFromUserName());
		assertNotNull("Should load from xml and set ToUserName propertiy.",
				voiceWxMessage.getToUserName());
	}

}
