/**
 * 
 */
package co.speedar.wechat.constant;

/**
 * States of changing a user's locale.
 * 
 * @author ben
 * @creation 2014年4月6日
 */
public class ChangeLocaleState {
	/**
	 * Initial state of changing locale business.<br/>
	 * Next state: PROMPT_SENT.<br/>
	 * Expected input: none.<br/>
	 * Action: send prompt to the user and go to the next state.
	 */
	public static final int INIT = BusinessType.CHANGE_LOCALE * 100;

	/**
	 * Prompt for changing locale has been sent to the user.<br/>
	 * Next state: DONE.<br/>
	 * Expected input: integer option representing different supported languages.<br/>
	 * Action: change the user's locale and put it into session.
	 */
	public static final int PROMPT_SENT = INIT + 1;

	/**
	 * Final state of changing a user's locale.<br/>
	 * Next state: none.<br/>
	 * Expected input: none.<br/>
	 * Action: go directly back to main menu.
	 */
	public static final int DONE = PROMPT_SENT + 1;
}
