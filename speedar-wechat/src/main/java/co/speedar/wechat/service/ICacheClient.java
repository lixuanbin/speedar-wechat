/**
 * 
 */
package co.speedar.wechat.service;

import java.io.Serializable;

/**
 * @author ben
 * @creation 2014年3月14日
 */
public interface ICacheClient {
	/**
	 * Set cache value with default timeout period.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setCacheValue(String key, Serializable value);

	/**
	 * Set cache value within a specific timeout period(seconds).
	 * 
	 * @param key
	 * @param value
	 * @param expiredSeconds
	 * @return
	 */
	public boolean setCacheValue(String key, Serializable value,
			int expiredSeconds);

	/**
	 * Retrieve a value from cache using a given key.
	 * 
	 * @param key
	 * @return
	 */
	public Serializable getCacheValue(String key);

	/**
	 * Delete a specific value using a given key.
	 * 
	 * @param key
	 */
	public void deleteCacheValue(String key);
}
