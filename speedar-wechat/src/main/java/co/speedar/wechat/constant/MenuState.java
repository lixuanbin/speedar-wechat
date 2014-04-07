/**
 * 
 */
package co.speedar.wechat.constant;

/**
 * @author ben
 * @creation 2014年4月6日
 */
public class MenuState {
	/**
	 * Initial state of menu business.
	 */
	public static final int INIT = BusinessType.MENU * 100;
	
	/**
	 * Menu has been sent to the user.
	 */
	public static final int MENU_SENT = INIT + 1;
}
