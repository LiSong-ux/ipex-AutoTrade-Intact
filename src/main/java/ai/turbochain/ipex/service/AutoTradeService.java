package ai.turbochain.ipex.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import ai.turbochain.ipex.entity.BaseParameter;
import ai.turbochain.ipex.entity.Market;
import ai.turbochain.ipex.entity.MarketParameter;
import ai.turbochain.ipex.entity.Order;
import ai.turbochain.ipex.entity.Target;
import ai.turbochain.ipex.entity.TradeParameter;
import ai.turbochain.ipex.mapper.BaseParameterMapper;
import ai.turbochain.ipex.mapper.MarketParameterMapper;
import ai.turbochain.ipex.mapper.TradeParameterMapper;
import ai.turbochain.ipex.runnable.AddOrderRunnable;
import ai.turbochain.ipex.runnable.GetMarketRunnable;
import ai.turbochain.ipex.utils.StyleUtil;

/**
 * 初始化交易条件，启动交易线程
 * 
 * @author 未央
 * @date 2019年10月11日 下午5:08:20
 *
 */
@Service
public class AutoTradeService {

	@Autowired
	private BaseParameterMapper baseParameterMapper;

	@Autowired
	private MarketParameterMapper marketParameterMapper;

	@Autowired
	private TradeParameterMapper tradeParameterMapper;

	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;

	private Market market = Market.getMarket();

	private Map<String, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<String, ScheduledFuture<?>>();

	private StyleUtil stylePack = new StyleUtil();

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		return new ThreadPoolTaskScheduler();
	}

	public String startAutoTrade(String token) {
		BaseParameter baseParameter = baseParameterMapper.getBaseParameter();
		int cycleLength = baseParameter.getCycleLength();
		String marketSymbol;

		List<MarketParameter> marketParameterList = marketParameterMapper.getMarketParameter();
		if (marketParameterList == null || marketParameterList.size() == 0) {
			return stylePack.getError("查询参数错误", "请设置查询参数", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}
		for (MarketParameter marketParameter : marketParameterList) {
			marketSymbol = marketParameter.getCoin().toLowerCase() + marketParameter.getBaseCoin().toLowerCase();
			getMarket(marketSymbol, cycleLength);
		}

		try {
			Thread.sleep(45000);
		} catch (InterruptedException e) {
			return e.toString();
		}

		List<TradeParameter> tradeParameterList = tradeParameterMapper.getTradeParameter();
		if (tradeParameterList == null || tradeParameterList.size() == 0) {
			return stylePack.getError("交易参数错误", "请设置交易参数", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}
		for (TradeParameter tradeParameter : tradeParameterList) {
			Order buyOrder = new Order();
			Order sellOrder = new Order();
			String symbol = tradeParameter.getCoin() + "/" + tradeParameter.getBaseCoin();

			buyOrder.setToken(token);
			buyOrder.setSymbol(symbol);
			buyOrder.setDirection("BUY");
			buyOrder.setType("LIMIT_PRICE");
			buyOrder.setUseDiscount("0");

			sellOrder.setToken(token);
			sellOrder.setSymbol(symbol);
			sellOrder.setDirection("SELL");
			sellOrder.setType("LIMIT_PRICE");
			sellOrder.setUseDiscount("0");

			marketSymbol = tradeParameter.getCoin().toLowerCase() + tradeParameter.getBaseCoin().toLowerCase();
			// Target target = JSON.parseObject(baseParameter.getTradeTarget(),
			// Target.class);
			// Target target = baseParameter.getTargetObj();

			autoTrade(buyOrder, sellOrder, marketSymbol, tradeParameter, baseParameter);
		}

		return stylePack.getSuccess("启动定时任务成功", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

	/**
	 * 创建查询行情线程
	 * 
	 * @param marketSymbol
	 * @param cycleLength
	 */
	public void getMarket(String marketSymbol, int cycleLength) {
		// 创建自动查询行情线程
		ScheduledFuture<?> getMarketFuture = threadPoolTaskScheduler
				.schedule(new GetMarketRunnable(marketSymbol, market), new Trigger() {

					@Override
					public Date nextExecutionTime(TriggerContext triggerContext) {
						return new CronTrigger("0/" + cycleLength + " * * * * ?").nextExecutionTime(triggerContext);
					}

				});
		taskMap.put(marketSymbol, getMarketFuture);
	}

	/**
	 * 创建自动挂单线程
	 * 
	 * @param buyOrder
	 * @param sellOrder
	 * @param marketSymbol
	 * @param priceAccuracy
	 * @param amountAccuracy
	 * @param cycleLength
	 * @param target
	 */
	public void autoTrade(Order buyOrder, Order sellOrder, String marketSymbol, TradeParameter tradeParameter,
			BaseParameter baseParameter) {
		// 创建自动买入线程
		ScheduledFuture<?> autoBuyFuture = threadPoolTaskScheduler.schedule(
				new AddOrderRunnable(buyOrder, marketSymbol, market, tradeParameter, baseParameter), new Trigger() {

					@Override
					public Date nextExecutionTime(TriggerContext triggerContext) {
						return new CronTrigger("0/" + baseParameter.getCycleLength() + " * * * * ?")
								.nextExecutionTime(triggerContext);
					}

				});
		// 创建自动卖出线程
		ScheduledFuture<?> autoSellFuture = threadPoolTaskScheduler.schedule(
				new AddOrderRunnable(sellOrder, marketSymbol, market, tradeParameter, baseParameter), new Trigger() {

					@Override
					public Date nextExecutionTime(TriggerContext triggerContext) {
						return new CronTrigger("0/" + baseParameter.getCycleLength() + " * * * * ?")
								.nextExecutionTime(triggerContext);
					}

				});

		taskMap.put(marketSymbol, autoBuyFuture);
		taskMap.put(marketSymbol, autoSellFuture);

	}

	/**
	 * 停止指定的定时任务
	 * 
	 * @param taskName
	 * @return
	 */
	public String stopTask(String taskName) {
		if (taskMap.get(taskName) == null) {
			return stylePack.getError("停止定时任务错误", "未找到指定的定时任务", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}
		taskMap.get(taskName).cancel(true);
		taskMap.remove(taskName);
		return stylePack.getSuccess("停止定时任务成功", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

	/**
	 * 停止所有的定时任务
	 * 
	 * @return
	 */
	public String stopAllTask() {
		if (taskMap.size() == 0) {
			return stylePack.getError("停止定时任务错误", "没有可停止的定时任务", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}
		Set<String> taskSet = taskMap.keySet();
		for (String taskName : taskSet) {
			taskMap.get(taskName).cancel(true);
			taskMap.remove(taskName);
		}
		return stylePack.getSuccess("停止全部定时任务成功", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

}
