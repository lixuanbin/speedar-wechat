package co.speedar.wechat.constant;

/**
 * The type code of different functions.<br/>
 * business type: [1, 99];<br/>
 * business state: type * 100 + [0, 99], 0 for initial state;<br/>
 * 
 * @author lixuanbin
 * @creation 2013-09-23
 */
public class BusinessType {
	/* Structure of business state:
	 *  _____ _____ 
	 * |__|__|__|__|
	 *  \   / \   /
	 *  type  state
	 */
	
	/**
	 * Main menu.
	 */
	public static final int MENU = 1;

	/**
	 * Echo back the user's text.
	 */
	public static final int ECHO = 2;
	
	/**
	 * Guess number game.
	 */
	public static final int GUESS_NUMBER = 3;
	
	/**
	 * Change user locale.
	 */
	public static final int CHANGE_LOCALE = 4;
}
