package co.speedar.wechat.util;

import org.apache.log4j.Logger;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;

/**
 * AES encryption and decryption tool.
 * 
 * @author ben
 * @creation 2014年3月20日
 */
@Component
public class AESWithoutJCE {
	protected static final Logger log = Logger.getLogger(AESWithoutJCE.class);

	private byte[] ivBytes = { 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31,
			0x38, 0x27, 0x36, 0x35, 0x34, 0x23, 0x32, 0x31 };

	/**
	 * Encrypt the content with a given key using aes algorithm.
	 * 
	 * @param content
	 * @param key
	 * 			must contain exactly 32 characters
	 * @return
	 */
	public String encrypt(String content, String key) {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null!");
		}
		if (key.length() != 32) {
			throw new IllegalArgumentException(
					"Key length must be 32 characters!");
		}
		String encrypted = null;
		byte[] keyBytes = key.getBytes();
		BufferedBlockCipher engine = new PaddedBufferedBlockCipher(
				new CBCBlockCipher(new AESFastEngine()));
		engine.init(true, new ParametersWithIV(new KeyParameter(keyBytes),
				ivBytes));
		byte[] enc = new byte[engine.getOutputSize(content.getBytes().length)];
		int size1 = engine.processBytes(content.getBytes(), 0,
				content.getBytes().length, enc, 0);
		int size2;
		try {
			size2 = engine.doFinal(enc, size1);
			byte[] encryptedContent = new byte[size1 + size2];
			System.arraycopy(enc, 0, encryptedContent, 0,
					encryptedContent.length);
			encrypted = new String(Hex.encode(encryptedContent));
		} catch (DataLengthException e) {
			log.error(e, e);
		} catch (IllegalStateException e) {
			log.error(e, e);
		} catch (InvalidCipherTextException e) {
			log.error(e, e);
		}
		return encrypted;
	}

	/**
	 * Decrypt the content with a given key using aes algorithm.
	 * 
	 * @param content
	 * @param key
	 * 			must contain exactly 32 characters
	 * @return
	 */
	public String decrypt(String content, String key) {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null!");
		}
		if (key.length() != 32) {
			throw new IllegalArgumentException(
					"Key length must be 32 characters!");
		}
		String decrypted = null;
		byte[] encryptedContent = Hex.decode(content);
		byte[] keyBytes = key.getBytes();
		BufferedBlockCipher engine = new PaddedBufferedBlockCipher(
				new CBCBlockCipher(new AESFastEngine()));
		engine.init(false, new ParametersWithIV(new KeyParameter(keyBytes),
				ivBytes));
		byte[] dec = new byte[engine.getOutputSize(encryptedContent.length)];
		int size1 = engine.processBytes(encryptedContent, 0,
				encryptedContent.length, dec, 0);
		int size2;
		try {
			size2 = engine.doFinal(dec, size1);
			byte[] decryptedContent = new byte[size1 + size2];
			System.arraycopy(dec, 0, decryptedContent, 0,
					decryptedContent.length);
			decrypted = new String(decryptedContent);
		} catch (DataLengthException e) {
			log.error(e, e);
		} catch (IllegalStateException e) {
			log.error(e, e);
		} catch (InvalidCipherTextException e) {
			log.error(e, e);
		}
		return decrypted;
	}

}