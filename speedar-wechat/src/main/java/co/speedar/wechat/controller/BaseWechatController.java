/**
 * 
 */
package co.speedar.wechat.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import co.speedar.wechat.exception.SpeedarException;
import co.speedar.wechat.exception.UnsupportedMessageTypeException;
import co.speedar.wechat.message.base.BaseReceivedMessage;
import co.speedar.wechat.message.base.BaseResponsedMessage;
import co.speedar.wechat.util.ControllerHelper;

/**
 * Extend this class and write your own business logic in the doProcess method.
 * 
 * @author lixuanbin
 * @creation 2014-04-5
 */
public abstract class BaseWechatController implements IWechatController {
	protected static Logger log = Logger.getLogger(BaseWechatController.class);

	@Autowired
	private ControllerHelper helper;

	public ControllerHelper getHelper() {
		return helper;
	}

	/**
	 * Map request xml into BaseReceivedMessage object.<br/>
	 * Override this method if you need to do something more.
	 * 
	 * @param requestXml
	 * @return
	 * @throws UnsupportedMessageTypeException
	 */
	public BaseReceivedMessage preProcess(String requestXml)
			throws UnsupportedMessageTypeException {
		BaseReceivedMessage receivedWxMessage = BaseReceivedMessage
				.fromXml(requestXml);
		return receivedWxMessage;
	}

	/**
	 * Override this method and write your own controller process here.
	 * 
	 * @param receivedWxMessage
	 * @return
	 */
	public abstract BaseResponsedMessage doProcess(
			BaseReceivedMessage receivedWxMessage) throws SpeedarException;

	/**
	 * Map BaseResponsedMessage into xml string.<br/>
	 * Override this method if you need to do something more.
	 * 
	 * @param responsedWxMessage
	 * @return
	 */
	public String postProcess(BaseResponsedMessage responsedWxMessage) {
		return (responsedWxMessage != null ? responsedWxMessage.toXml() : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.meetwe.front.controller.IWechatController#handleRequest(java.lang
	 * .String)
	 */
	@Override
	public String handleRequest(String requestXmlString)
			throws SpeedarException {
		String resultXmlString = null;
		BaseReceivedMessage receivedWxMessage;
		BaseResponsedMessage responsedWxMessage;
		receivedWxMessage = preProcess(requestXmlString);
		responsedWxMessage = doProcess(receivedWxMessage);
		resultXmlString = postProcess(responsedWxMessage);
		return resultXmlString;
	}
}
