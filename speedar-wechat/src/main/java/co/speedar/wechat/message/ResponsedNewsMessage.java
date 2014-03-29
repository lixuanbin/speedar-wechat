/**
 * 
 */
package co.speedar.wechat.message;

import co.speedar.wechat.message.base.BaseResponsedMessage;
import co.speedar.wechat.util.MessageDateConverter;

import com.thoughtworks.xstream.XStream;

/**
 * @author lixuanbin
 * @creation 2012-11-21
 */
public class ResponsedNewsMessage extends BaseResponsedMessage {
	private int ArticleCount;
	private Article[] Articles;

	public ResponsedNewsMessage() {

	}

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public Article[] getArticles() {
		return Articles;
	}

	public void setArticles(Article[] articles) {
		Articles = articles;
	}

	@Override
	public String toXml() {
		XStream xstream = new XStream();
		MessageDateConverter converter = new MessageDateConverter();
		xstream.registerConverter(converter);
		xstream.alias("xml", ResponsedNewsMessage.class);
		xstream.alias("item", Article.class);
		String xmlString = xstream.toXML(this);
		xstream = null;
		converter = null;
		return xmlString;
	}
}
