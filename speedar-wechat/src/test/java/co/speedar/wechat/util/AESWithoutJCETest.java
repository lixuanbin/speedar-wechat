/**
 * 
 */
package co.speedar.wechat.util;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ben
 * @creation 2014年3月28日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:testContext.xml",
		"classpath:speedarWechatContext.xml" })
public class AESWithoutJCETest {
	private AESWithoutJCE aes;

	@Before
	public void init() {
		aes = new AESWithoutJCE();
	}

	@Test
	public void testEncryAndDecrypt() {
		String key = "123456789012345678901234567890~!";
		String content = "this is a secret.";
		System.out.println("original content: " + content);
		String encrypted = null;
		String decrypted = null;
		encrypted = aes.encrypt(content, key);
		System.out.println("encrypted content: " + encrypted);
		decrypted = aes.decrypt(encrypted, key);
		System.out.println("decrypted content: " + decrypted);
		assertTrue(
				"The content after encrypted and decrypted should be the same as before.",
				content.equals(decrypted));
	}
}
