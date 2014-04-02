/**
 * 
 */
package co.speedar.wechat.domain;

import java.io.Serializable;

/**
 * 微信签名封装类
 * 
 * @author lixuanbin
 * @creation 2013-2-23
 */
public class WechatSignature implements Serializable {
	private static final long serialVersionUID = -7802560606542111748L;
	private String signature;
	private String timestamp;
	private String nonce;

	public WechatSignature() {
	}

	public WechatSignature(String signature, String timestamp, String nonce) {
		this.signature = signature;
		this.timestamp = timestamp;
		this.nonce = nonce;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
}
