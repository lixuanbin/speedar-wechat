/**
 * 
 */
package co.speedar.wechat.exception;

/**
 * @author ben
 * @creation 2014年3月29日
 */
public class SessionTimeoutException extends SpeedarException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8576164056138876964L;

	public static final String defaultCode = "session.timeout";

	public SessionTimeoutException(String message) {
		super(defaultCode, message);
	}

	public SessionTimeoutException(String code, String message) {
		super(code, message);
	}
}
