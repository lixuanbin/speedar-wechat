/**
 * 
 */
package co.speedar.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author ben
 * @creation 2014年3月27日
 */
@Component
public class XmlTool {
	protected static Logger logger = Logger.getLogger(XmlTool.class);

	/**
	 * Extract value by the given tag name
	 * 
	 * @param xmlString
	 * @param tagName
	 * @return
	 */
	public static String getValueByTag(String xmlString, String tagName) {
		String resultString = "";
		xmlString = StringUtils.trim(xmlString);
		tagName = StringUtils.trim(tagName);
		if (StringUtils.contains(xmlString, tagName)) {
			String beginString = "<" + tagName + ">";
			String endString = "</" + tagName + ">";
			resultString = StringUtils.substringBetween(xmlString, beginString,
					endString);
			if (StringUtils.contains(resultString, "<![CDATA[")
					&& StringUtils.contains(resultString, "]]>")) {
				resultString = StringUtils.substringBetween(resultString,
						"<![CDATA[", "]]>");
			}
			beginString = null;
			endString = null;
		}
		return resultString;
	}

	/**
	 * Extract xml string from the inputStream.
	 * 
	 * @param inputStream
	 * @param charsetName
	 * @return
	 */
	private String getXmlStringFromInputStream(InputStream inputStream,
			String charsetName) {
		String resultXmlString = "";
		String tempString = null;
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream, charsetName));
			tempString = bufferedReader.readLine();
			while (tempString != null) {
				resultXmlString += tempString;
				tempString = bufferedReader.readLine();
			}
			tempString = null;
			bufferedReader.close();
			bufferedReader = null;
		} catch (UnsupportedEncodingException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		}
		return StringUtils.trim(resultXmlString);
	}

	/**
	 * Extract xml string form http request.
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public String getXmlStringFromHttpRequest(HttpServletRequest request) {
		String requestXmlString = "";
		try {
			InputStream inputStream = request.getInputStream();
			String encoding = StringUtils.isNotBlank(request
					.getCharacterEncoding()) ? request.getCharacterEncoding()
					: "utf-8";
			requestXmlString = getXmlStringFromInputStream(inputStream,
					encoding);
			encoding = null;
			inputStream.close();
			inputStream = null;
		} catch (IOException e) {
			logger.error(e, e);
		}

		return requestXmlString;
	}

	/**
	 * Extract value by the given tag name
	 * 
	 * @param xmlString
	 * @param tagName
	 * @return
	 */
	public String getValueByTagName(String xmlString, String tagName) {
		return getValueByTag(xmlString, tagName);
	}

}
