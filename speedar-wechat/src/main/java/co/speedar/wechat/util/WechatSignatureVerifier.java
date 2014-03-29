/**
 * 
 */
package co.speedar.wechat.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lixuanbin
 * @creation 2013-1-30
 */
@Component("signatureVerifier")
public class WechatSignatureVerifier {
	protected static Logger log = Logger
			.getLogger(WechatSignatureVerifier.class);

	@Autowired
	private WechatSignatureEncryptor encryptor;

	@Autowired
	private SpeedarWechatCredential credential;

	@Value("${token.encryption.algorithm}")
	private String encryptionAlgorithm;

	@Value("${signature.valid.seconds}")
	private int signatureValidSeconds;

	/**
	 * Verify wechat signature.
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public boolean isValid(String signature, String timestamp, String nonce) {
		boolean isValid = false;
		if (StringUtils.isNotBlank(signature)
				&& StringUtils.isNotBlank(timestamp)
				&& StringUtils.isNotBlank(nonce)) {
			List<String> srcList = new ArrayList<String>();
			srcList.add(timestamp);
			srcList.add(nonce);
			srcList.add(credential.getToken());
			Collections.sort(srcList);
			StringBuffer srcBuffer = new StringBuffer();
			for (int i = 0; i < srcList.size(); i++) {
				srcBuffer.append(srcList.get(i));
			}
			String calculatedSignature = encryptor.encrypt(
					srcBuffer.toString(), encryptionAlgorithm);
			if (StringUtils.equalsIgnoreCase(signature, calculatedSignature)) {
				isValid = true;
			}
		}
		return isValid;
	}
}
