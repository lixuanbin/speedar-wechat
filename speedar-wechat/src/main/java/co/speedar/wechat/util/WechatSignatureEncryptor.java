/**
 * 
 */
package co.speedar.wechat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author lixuanbin
 * @creation 2013-1-30
 */
@Component("signatureEncryptor")
public class WechatSignatureEncryptor {
	protected static Logger log = Logger
			.getLogger(WechatSignatureEncryptor.class);

	/**
	 * 使用指定算法加密字符串，默认是md5
	 * 
	 * @param strSrc
	 *            , a string will be encrypted; <br/>
	 * @param encName
	 *            , the algorithm name will be used, dafault to "MD5"; <br/>
	 * @return
	 */
	public String encrypt(String strSrc, String encName) {
		// parameter strSrc is a string will be encrypted,
		// parameter encName is the algorithm name will be used.
		// encName dafault to "MD5"
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			if (encName == null || encName.equals("")) {
				encName = "MD5";
			}
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = HexStringBytesTool.bytesToHexString(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			log.error("Invalid algorithm: " + encName);
			return null;
		}
		return strDes;
	}
}
