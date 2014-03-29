/**
 * 
 */
package co.speedar.wechat.message;

import co.speedar.wechat.message.base.BaseResponsedMessage;

/**
 * @author lixuanbin
 * @creation 2012-11-21
 */
public class ResponsedMusicMessage extends BaseResponsedMessage {
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}

	public ResponsedMusicMessage() {

	}

}
