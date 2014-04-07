/**
 * 
 */
package co.speedar.wechat.controller;

import co.speedar.wechat.exception.SpeedarException;

/**
 * @author lixuanbin
 */
public interface IWechatController {
	/**
	 * Handles requests from wechat server
	 * 
	 * @param MsgType
	 * @param requestXmlString
	 * @return
	 */
	public String handleRequest(String requestXmlString)
			throws SpeedarException;
}
