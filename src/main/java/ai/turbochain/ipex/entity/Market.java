package ai.turbochain.ipex.entity;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 交易行情信息，确保单例
 * @author 未央
 * @date 2019年9月12日 上午9:23:39
 *
 */
public class Market {
	
	private static Market market = new Market();
	
	private Market() {
		this.marketMap = new HashMap<String, JSONObject>();
	}
	
	public static Market getMarket() {
		return market;
	}
	
	private Map<String, JSONObject> marketMap; 

	public Map<String, JSONObject> getMarketMap() {
		return marketMap;
	}

	public void setMarketMap(Map<String, JSONObject> marketMap) {
		this.marketMap = marketMap;
	}

}
