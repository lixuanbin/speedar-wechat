/**
 * 
 */
package co.speedar.wechat.constant;

/**
 * States of the echo business.
 * 
 * @author ben
 * @creation 2014年4月6日
 */
public class EchoState {
	/**
	 * Initial state of echo business.<br/>
	 * Next state: ECHO.<br/>
	 * Expected input: none.<br/>
	 * Action: send prompt to the user and go to the next state.
	 */
	public static final int INIT = BusinessType.ECHO * 100;

	/**
	 * Keep echoing back the user's input text until "?" is received.<br/>
	 * Next state: none.<br/>
	 * Expected input: any text message.<br/>
	 * Action: echo back the text to the user.
	 */
	public static final int ECHO = INIT + 1;
}
