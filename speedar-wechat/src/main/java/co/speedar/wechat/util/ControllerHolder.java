/**
 * 
 */
package co.speedar.wechat.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import co.speedar.wechat.constant.BusinessType;
import co.speedar.wechat.controller.IWechatController;

/**
 * Contain all wechat controllers except event and menu.
 * 
 * @author lixuanbin
 */
@Component
public class ControllerHolder {
	private Map<Integer, IWechatController> controllerMap;

	@Autowired
	@Qualifier("echoWechatController")
	private IWechatController echoWechatController;

	@Autowired
	@Qualifier("guessNumberWechatController")
	private IWechatController guessNumberWechatController;

	@Autowired
	@Qualifier("changeLocaleWechatController")
	private IWechatController changeLocaleWechatController;

	/**
	 * Initialize the controller map, put all the wechat controllers into it.
	 */
	@PostConstruct
	public void setUpControllerMap() {
		controllerMap = new HashMap<Integer, IWechatController>();
		controllerMap.put(BusinessType.ECHO, echoWechatController);
		controllerMap.put(BusinessType.GUESS_NUMBER,
				guessNumberWechatController);
		controllerMap.put(BusinessType.CHANGE_LOCALE,
				changeLocaleWechatController);
	}

	/**
	 * Get an appropriate controller by business type.
	 * 
	 * @param businessType
	 * @return
	 */
	public IWechatController getController(int businessType) {
		return controllerMap.get(businessType);
	}
}
