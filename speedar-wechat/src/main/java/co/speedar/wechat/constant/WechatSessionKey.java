/**
 * 
 */
package co.speedar.wechat.constant;

/**
 * Enumerable keys for holding session values.
 * 
 * @author ben
 * @creation 2014年3月23日
 */
public enum WechatSessionKey {
	TEST("testOnly"), WEIXIN_SIGNATURE("weixinSignature"), BUSINESS_TYPE(
			"businessType"), BUSINESS_STATE("businessState"), WECHAT_USER(
			"wechatUser"), LOCALE("locale");

	private final String name;

	private WechatSessionKey(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static WechatSessionKey getKeyByName(String name) {
		for (WechatSessionKey key : WechatSessionKey.values()) {
			if (key.getName().equalsIgnoreCase(name)) {
				return key;
			}
		}
		return null;
	}
}
