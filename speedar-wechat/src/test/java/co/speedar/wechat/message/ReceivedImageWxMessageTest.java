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
public class ReceivedImageWxMessageTest {
	@Value("${requestImageXml}")
	private String requestImageXml;

	@Test
	public void testStaticFromXml() throws UnsupportedMessageTypeException {
		ReceivedImageMessage imageWxMessage = (ReceivedImageMessage) BaseReceivedMessage
				.fromXml(requestImageXml);
		assertTrue(
				"MsgType should be image",
				StringUtils.equalsIgnoreCase("image",
						imageWxMessage.getMsgType()));
		assertNotNull("Should load from xml and set PicUrl propertiy.",
				imageWxMessage.getPicUrl());
		assertNotNull("Should load from xml and set MediaId propertiy.",
				imageWxMessage.getMediaId());
		assertNotNull("Should load from xml and set FromUserName propertiy.",
				imageWxMessage.getFromUserName());
		assertNotNull("Should load from xml and set ToUserName propertiy.",
				imageWxMessage.getToUserName());
	}
}
