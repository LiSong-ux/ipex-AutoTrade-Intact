package ai.turbochain.ipex.runnable;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ai.turbochain.ipex.entity.Market;
import ai.turbochain.ipex.utils.HttpUtil;

public class GetMarketRunnable implements Runnable {

	Market market;
	public String marketSymbol;
	
	public GetMarketRunnable(String marketSymbol, Market market) {
		this.market = market;
		this.marketSymbol = marketSymbol;
	}

	@Override
	public void run() {
		String result = null;
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("symbol", marketSymbol);
		try {
			result = HttpUtil.doGet("https://openapi.cdaevip.com/exchange-open-api/open/api/get_trades", params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject resultObj = JSON.parseObject(result);
		JSONArray data = resultObj.getJSONArray("data");
		List<String> list = JSONArray.parseArray(data.toJSONString(), String.class);
		JSONObject marketJSON = JSON.parseObject(list.get(0));
		market.getMarketMap().put(marketSymbol, marketJSON);
		
//		System.out.println("....."+marketSymbol+"："+list.get(0)+"......");
	}

}
