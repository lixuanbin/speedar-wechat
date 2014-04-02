/**
 * 
 */
package co.speedar.wechat.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import co.speedar.wechat.constant.WechatSessionKey;

/**
 * Simulates a session for a wechat user.<br/>
 * All the stuff you put into it must be of basic type or serializable.
 * 
 * @author lixuanbin
 * @creation 2012-12-17
 */
public class WechatSession implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1188486241672319204L;
	private long creationTime;
	private long lastAccessedTime;
	private Map<WechatSessionKey, Serializable> sessionMap;

	public WechatSession() {
		creationTime = System.currentTimeMillis();
		lastAccessedTime = System.currentTimeMillis();
		sessionMap = new HashMap<WechatSessionKey, Serializable>();
	}

	/**
	 * Returns the object bound with the specified name in this session, or null
	 * if no object is bound under the name.
	 * 
	 * @param key
	 * @return
	 */
	public Serializable getAttribute(WechatSessionKey key) {
		lastAccessedTime = System.currentTimeMillis();
		return sessionMap.get(key);
	}

	/**
	 * Binds an object to this session, using the name specified. If an object
	 * of the same name is already bound to the session, the object is replaced.
	 * 
	 * @param key
	 * @param value
	 */
	public void setAttribute(WechatSessionKey key, Serializable value) {
		lastAccessedTime = System.currentTimeMillis();
		sessionMap.put(key, value);
	}

	/**
	 * Removes the object bound with the specified name from this session. If
	 * the session does not have an object bound with the specified name, this
	 * method does nothing.
	 * 
	 * @param key
	 */
	public void removeAttribute(WechatSessionKey key) {
		lastAccessedTime = System.currentTimeMillis();
		sessionMap.remove(key);
	}

	/**
	 * Invalidates this session then unbinds any objects bound to it.
	 */
	public void invalidate() {
		sessionMap.clear();
	}

	/**
	 * @return
	 */
	public long getCreationTime() {
		return creationTime;
	}

	/**
	 * @return
	 */
	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

}
