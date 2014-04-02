/**
 * 
 */
package co.speedar.wechat.service.impl;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import co.speedar.wechat.service.ICacheClient;

import com.whalin.MemCached.MemCachedClient;

/**
 * ICacheClient's implementation, using memcached.
 * 
 * @author ben
 * @creation 2014年3月14日
 */
@Component
public class MemCacheClient implements ICacheClient {
	@Autowired
	public MemCachedClient mc;

	@Value("${maxInactiveSeconds}")
	private int maxInactiveSeconds;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.meetwe.front.service.ICacheClient#setCacheValue(java.lang.String,
	 * java.io.Serializable)
	 */
	@Override
	public boolean setCacheValue(String key, Serializable value) {
		return setCacheValue(key, value, maxInactiveSeconds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.meetwe.front.service.ICacheClient#setCacheValue(java.lang.String,
	 * java.io.Serializable, int)
	 */
	@Override
	public boolean setCacheValue(String key, Serializable value,
			int expiredSeconds) {
		if (expiredSeconds > 0) {
			if (StringUtils.isNotBlank(key)) {
				return mc.set(key, value, new Date(expiredSeconds * 1000));
			} else {
				throw new IllegalArgumentException(
						"key cannot be an empty string");
			}
		} else {
			throw new IllegalArgumentException(
					"expiredSeconds must be a positive integer");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.meetwe.front.service.ICacheClient#getCacheValue(java.lang.String)
	 */
	@Override
	public Serializable getCacheValue(String key) {
		if (StringUtils.isNotBlank(key)) {
			Object temp = mc.get(key);
			return temp != null ? (Serializable)temp : null;
		} else {
			throw new IllegalArgumentException("key cannot be blank.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.meetwe.front.service.ICacheClient#deleteCacheValue(java.lang.String)
	 */
	@Override
	public void deleteCacheValue(String key) {
		if (StringUtils.isNotBlank(key)) {
			mc.delete(key);
		} else {
			throw new IllegalArgumentException("key cannot be an empty string.");
		}
	}

}
