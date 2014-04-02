/**
 * 
 */
package co.speedar.wechat.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Load sensitive properties value and decrypt them (if encrypted).<br/>
 * The key for encryption and decryption is passed through -D parameter in the name of "speedarkey".<br/>
 * You can: <br/>
 * 1. Run this main method to encrypt relative properties above using your own key
 *  and set them back into the speedarWechat.properties file.<br/>
 * or: <br/>
 * 2. Turn the "isEncrypted" property off and set them in plain text in the properties file.
 * @author ben
 * @creation 2014年3月28日
 */
@Component("credential")
public class SpeedarWechatCredential {
	protected static final Logger log = Logger
			.getLogger(SpeedarWechatCredential.class);
	@Value("${isEncrypted}")
	private String isEncrypted;

	@Value("${token}")
	private String token;

	@Value("${domain.name}")
	private String domainName;

	@Value("${account.id}")
	private String accountId;

	@Autowired
	private AESWithoutJCE aes;

	/**
	 * Get key from environment variable and decrypt sensitive data.
	 * 
	 * @see co.speedar.wechat.util.AESWithoutJCE#decrypt(String, String)
	 */
	@PostConstruct
	public void initialize() {
		// Check if sensitive data is encrypted.
		if (StringUtils.equalsIgnoreCase(isEncrypted, "true")) {
			String speedarkey;
			try {
				// Get key for encryption and decryption form system environment.
				speedarkey = System.getenv("speedarkey");
				// Decrypt sensitive data.
				token = aes.decrypt(token, speedarkey);
				domainName = aes.decrypt(domainName, speedarkey);
				accountId = aes.decrypt(accountId, speedarkey);
			} catch (Throwable e) {
				log.error(e, e);
			}
		}
	}

	/**
	 * Return wechat api token.
	 * 
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Return the domain name.
	 * 
	 * @return
	 */
	public String getDomainName() {
		return domainName;
	}

	public String getAccountId() {
		return accountId;
	}

	/**
	 * Get a string from command line input and encrypt it.
	 * 
	 * @see co.speedar.wechat.util.AESWithoutJCE#encrypt(String, String)
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		System.out.println("Enter the content you wanna encrypt: ");
		String content = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		content = br.readLine();
		System.out.println();
		String speedarkey = System.getenv("speedarkey");
		if (StringUtils.isBlank(speedarkey)) {
			System.out.println("Enter the key: ");
			speedarkey = br.readLine();
		}
		// Decrypt sensitive data.
		AESWithoutJCE aes = new AESWithoutJCE();
		content = aes.encrypt(content, speedarkey);
		System.out.println("Content after encryption: \n" + content);
		System.out.println("Content after decryption: \n"
				+ aes.decrypt(content, speedarkey));
	}
}
