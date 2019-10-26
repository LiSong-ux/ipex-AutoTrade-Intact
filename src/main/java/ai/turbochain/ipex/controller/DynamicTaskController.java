package ai.turbochain.ipex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.turbochain.ipex.entity.Market;
import ai.turbochain.ipex.service.AutoTradeService;

/**
 * 自动交易机器人
 * 
 * @author 未央
 * @date 2019年9月11日 上午10:19:33
 *
 */
@RestController
@RequestMapping("/task")
public class DynamicTaskController {
	
	@Autowired
	private AutoTradeService autoTradeService;
	
	private Market market = Market.getMarket();

	/**
	 * 启动自动交易线程
	 * @return
	 */
	@RequestMapping("/start/{token}")
	public String getMarket(@PathVariable String token) {
		String result = autoTradeService.startAutoTrade(token);
		return result;
	}

	/**
	 * 结束自动成交线程
	 * @return
	 */
	@RequestMapping("/stop/{taskName}")
	public String stopTrade(@PathVariable String taskName) {
		String result;
		if (taskName.equals("all")) {
			result = autoTradeService.stopAllTask();
		} else {
			result = autoTradeService.stopTask(taskName);
		}
		return result;
	}

}
