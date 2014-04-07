/**
 * 
 */
package co.speedar.wechat.constant;

/**
 * Guess number game's states.
 * 
 * @author ben
 * @creation 2014年4月6日
 */
public class GuessNumberState {
	/**
	 * Initial state of guessing number business.<br/>
	 * Next state: PROMPT_SENT.<br/>
	 * Expected input: none.<br/>
	 * Action: generate an integer, put it into the session, send prompt to the user and go to the next state.
	 */
	public static final int INIT = BusinessType.GUESS_NUMBER * 100;

	/**
	 * Prompt message has been sent to the user.<br/>
	 * Next state: TOO_BIG | TOO_SMALL.<br/>
	 * Expected input: integer.<br/>
	 * Action: wait for the user's input.
	 */
	public static final int PROMPT_SENT = INIT + 1;

	/**
	 * The user's input is bigger than the answer.<br/>
	 * Next state: TOO_BIG | TOO_SMALL | BINGO.<br/>
	 * Expected input: integer.<br/>
	 * Action: send prompt for too big and wait for the user's input.
	 */
	public static final int TOO_BIG = PROMPT_SENT + 1;

	/**
	 * The user's input is smaller than the answer.<br/>
	 * Next state: TOO_BIG | TOO_SMALL | BINGO.<br/>
	 * Expected input: integer.<br/>
	 * Action: send prompt for too small and wait for the user's input.
	 */
	public static final int TOO_SMALL = TOO_BIG + 1;

	/**
	 * The user's input is right.<br/>
	 * Next state: none.<br/>
	 * Expected input: none.<br/>
	 * Action: send congratulations and say goodbye.
	 */
	public static final int BINGO = TOO_SMALL + 1;
}
