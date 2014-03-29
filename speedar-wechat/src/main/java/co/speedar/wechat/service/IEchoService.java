/**
 * 
 */
package co.speedar.wechat.service;

import co.speedar.wechat.message.base.BaseReceivedMessage;
import co.speedar.wechat.message.base.BaseResponsedMessage;

/**
 * @author ben
 * @creation 2014年3月27日
 */
public interface IEchoService {

	/**
	 * Echo back the origin wechat message.
	 * 
	 * @param message
	 * @return
	 */
	public BaseResponsedMessage echo(BaseReceivedMessage message);
}
