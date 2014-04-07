package co.speedar.wechat.controller;

/**
 * @author ben
 * @creation 2014年4月6日
 */
public interface IWechatRequestDispatcher {

	/**
	 * 1.Dispatch the request xml string to an appropriate controller.<br/>
	 * 2.Exception handling.
	 * 
	 * @param requestXmlString
	 * @return
	 */
	public String dispatch(String requestXmlString);

}