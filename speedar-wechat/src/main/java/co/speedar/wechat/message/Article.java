package co.speedar.wechat.message;

import java.io.Serializable;

/**
 * @author lixuanbin
 * @creation 2012-11-21
 */
public class Article implements Serializable {
	private static final long serialVersionUID = -8456299641136406653L;

	private String Title;
	private String Discription;
	private String PicUrl;
	private String Url;

	public Article() {
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDiscription() {
		return Discription;
	}

	public void setDiscription(String discription) {
		Discription = discription;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
}
