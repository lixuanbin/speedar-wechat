/**
 * 
 */
package co.speedar.wechat.exception;

/**
 * @author ben
 * @creation 2014年3月29日
 */
public class UnsupportedMessageTypeException extends SpeedarException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8576163756138876964L;

	public static final String defaultCode = "unsupported.message.type";

	public UnsupportedMessageTypeException(String message) {
		super(defaultCode, message);
	}

	public UnsupportedMessageTypeException(String code, String message) {
		super(code, message);
	}
}
