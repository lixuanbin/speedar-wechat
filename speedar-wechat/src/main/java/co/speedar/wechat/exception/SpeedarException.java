/**
 * 
 */
package co.speedar.wechat.exception;

/**
 * @author ben
 * @creation 2014年4月6日
 */
public class SpeedarException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7766795664262365388L;

	/**
	 * The code used to look for a user friendly error message in the i18n properties file.
	 */
	private String code;

	/**
	 * Extra error message to be supplied for the developer.
	 */
	private String message;

	public SpeedarException() {
	}

	public SpeedarException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
