package co.speedar.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import co.speedar.wechat.message.base.BaseReceivedMessage;
import co.speedar.wechat.service.IEchoService;
import co.speedar.wechat.util.WechatSignatureVerifier;
import co.speedar.wechat.util.XmlTool;

/**
 * Servlet implementation class SpeedarWechatServlet
 */
@WebServlet(urlPatterns = { "/wechat.do" }, loadOnStartup = 1)
public class SpeedarWechatServlet extends HttpServlet {
	protected static final Logger log = Logger
			.getLogger(SpeedarWechatServlet.class);
	private static final long serialVersionUID = 6536688299231165548L;

	@Value("${mode}")
	private String mode;

	@Autowired
	private XmlTool xmlTool;

	@Autowired
	private WechatSignatureVerifier verifier;

	@Autowired
	private IEchoService echoService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SpeedarWechatServlet() {
		super();
	}

	/**
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String echostr = request.getParameter("echostr");
		log.info("echostr before echo: " + echostr);
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		// Need to check signature in product mode.
		if (verifier.isValid(signature, timestamp, nonce)
				|| !StringUtils.equalsIgnoreCase("prod", mode)) {
			PrintWriter writer = response.getWriter();
			log.info("echostr after echo: " + echostr);
			writer.print(echostr);
			writer.flush();
			writer.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Get request parameters
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");

		// Get xml data
		String encoding = StringUtils
				.isNotBlank(request.getCharacterEncoding()) ? request
				.getCharacterEncoding() : "utf-8";
		String requestXmlString = xmlTool.getXmlStringFromHttpRequest(request);
		log.info("received xml data:\n" + requestXmlString);

		// Process incoming wechat request
		if (verifier.isValid(signature, timestamp, nonce)
				|| !StringUtils.equalsIgnoreCase("prod", mode)) {
			try {
				String responseXmlString = echoService.echo(
						BaseReceivedMessage.fromXml(requestXmlString))
						.toString();
				log.info("responsed xml data:\n" + responseXmlString);
				response.setCharacterEncoding(encoding);
				PrintWriter writer = response.getWriter();
				writer.print(responseXmlString);
				writer.flush();
				writer.close();
			} catch (Exception e) {
				log.error(e, e);
			}
		}
	}

}
