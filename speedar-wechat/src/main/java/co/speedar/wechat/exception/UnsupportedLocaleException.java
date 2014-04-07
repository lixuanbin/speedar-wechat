/**
 * 
 */
package co.speedar.wechat.exception;

/**
 * Unsupported locale exception.
 * 
 * @author ben
 * @creation 2014年3月29日
 */
public class UnsupportedLocaleException extends SpeedarException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8576163767138876964L;

	public static final String defaultCode = "unsupported.locale";

	/**
	 * @param message
	 */
	public UnsupportedLocaleException(String message) {
		super(defaultCode, message);
	}

	/**
	 * @param code
	 * @param message
	 */
	public UnsupportedLocaleException(String code, String message) {
		super(code, message);
	}
}
