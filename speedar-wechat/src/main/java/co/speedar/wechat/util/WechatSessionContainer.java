/**
 * 
 */
package co.speedar.wechat.util;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import co.speedar.wechat.service.ICacheClient;

/**
 * @author lixuanbin
 * @creation 2012-12-31
 */
@Component("sessionContainer")
public class WechatSessionContainer {
	protected static Logger log = Logger
			.getLogger(WechatSessionContainer.class);

	@Value("${maxInactiveSeconds}")
	private int defaultSessionTimeoutSeconds;

	@Value("${defaultGlobalTimeoutSeconds}")
	private int defaultGlobalTimeoutSeconds;

	@Autowired
	private ICacheClient cacheClient;

	public WechatSessionContainer() {
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setGlobalValue(String key, Serializable value) {
		return setGlobalValue(key, value, defaultGlobalTimeoutSeconds);
	}

	/**
	 * @param key
	 * @param value
	 * @param expiredSeconds
	 * @return
	 */
	public boolean setGlobalValue(String key, Serializable value,
			int expiredSeconds) {
		if (StringUtils.isNotBlank(key) && value != null) {
			return cacheClient.setCacheValue(key, value, expiredSeconds);
		}
		return false;
	}

	/**
	 * @param key
	 * @return
	 */
	public Object getGlobalValue(String key) {
		if (StringUtils.isBlank(key)) {
			throw new IllegalArgumentException("Key cannot be empty!");
		}
		return cacheClient.getCacheValue(key);
	}

	/**
	 * Retrieve a specified wechat user session from cache, or:<br/>
	 * new a session, set it into cache server and return it if the get from
	 * cache operation returns null.
	 * 
	 * @param openid
	 * @return
	 */
	public WechatSession getSession(String openid) {
		if (StringUtils.isBlank(openid)) {
			throw new IllegalArgumentException("Openid cannot be empty!");
		}
		Object temp = getGlobalValue(openid);
		WechatSession WechatSession = null;
		if (temp == null) {
			WechatSession = new WechatSession();
			setGlobalValue(openid, WechatSession, defaultSessionTimeoutSeconds);
		} else {
			WechatSession = (WechatSession) temp;
		}
		return WechatSession;
	}

	/**
	 * Set the specified wechat user session using default timeout value.
	 * 
	 * @param openid
	 * @return
	 */
	public boolean setSession(String openid, WechatSession session) {
		if (StringUtils.isBlank(openid)) {
			throw new IllegalArgumentException("Openid cannot be empty!");
		}
		return setSession(openid, session, defaultSessionTimeoutSeconds);
	}

	/**
	 * Set the specified wechat user session using a given timeout.
	 * 
	 * @param openid
	 * @param session
	 * @param expiredSeconds
	 *            a positive integer
	 * @return true if the set operation is ok
	 */
	public boolean setSession(String openid, WechatSession session,
			int expiredSeconds) {
		if (expiredSeconds > 0) {
			return cacheClient.setCacheValue(openid, session, expiredSeconds);
		} else {
			throw new IllegalArgumentException(
					"expiredSeconds must be positive.");
		}
	}

	/**
	 * Remove a specified wechat user session.
	 * 
	 * @param openid
	 */
	public void removeSession(String openid) {
		if (StringUtils.isBlank(openid)) {
			throw new IllegalArgumentException("Openid cannot be empty!");
		}
		cacheClient.deleteCacheValue(openid);
	}

	/**
	 * @return
	 */
	public int getMaxInactiveSeconds() {
		return defaultSessionTimeoutSeconds;
	}

	/**
	 * Specifies the time, in seconds, between client requests before the 
	 * container will invalidate this session.
	 * 
	 * @param interval
	 *            a positive integer
	 */
	public void setMaxInactiveSeconds(int interval) {
		if (interval > 0) {
			this.defaultSessionTimeoutSeconds = interval;
		} else {
			throw new IllegalArgumentException(
					"interval must be a positive integer.");
		}
	}

}