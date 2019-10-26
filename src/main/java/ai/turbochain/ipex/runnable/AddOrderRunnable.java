package ai.turbochain.ipex.runnable;

import java.io.IOException;
import java.net.URISyntaxException;
import com.alibaba.fastjson.JSONObject;

import ai.turbochain.ipex.entity.BaseParameter;
import ai.turbochain.ipex.entity.Market;
import ai.turbochain.ipex.entity.Order;
import ai.turbochain.ipex.entity.Target;
import ai.turbochain.ipex.entity.TradeParameter;
import ai.turbochain.ipex.utils.HttpUtil;

public class AddOrderRunnable implements Runnable {

	Market market;
	private Order order;
	private String marketSymbol;
	private int priceAccuracy;
	private int amountAccuracy;
	private Target target;
	private double priceRate;
	private double amountRate;

	public AddOrderRunnable(Order order, String marketSymbol, Market market, TradeParameter tradeParameter,
			BaseParameter baseParameter) {
		this.market = market;
		this.order = order;
		this.marketSymbol = marketSymbol;
		this.priceAccuracy = tradeParameter.getPriceAccuracy();
		this.amountAccuracy = tradeParameter.getAmountAccuracy();
		this.target = baseParameter.getTargetObj();
		this.priceRate = baseParameter.getPriceRate();
		this.amountRate = baseParameter.getAmountRate();
	}

	@Override
	public void run() {
		// Target target = new Target();
		// target.setProtocol("https");
		// target.setSite("ipex.turbochain.ai");
		// target.setPath("/api/exchange-api/exchange/order/add");

		JSONObject marketJSON = market.getMarketMap().get(marketSymbol);

		double marketPrice = 0;
		double price = 0;
		double marketAmount = 0;

		if (!marketSymbol.equals("eosusdt") && marketJSON == null) {
			marketJSON = market.getMarketMap().get("ltceth");
			marketPrice = new Double(marketJSON.getString("price"));
			price = getRound(marketPrice * priceRate, priceAccuracy);
			marketAmount = new Double(marketJSON.getString("amount"));
		} else if (marketSymbol.equals("eosusdt") && marketJSON == null) {
			JSONObject marketJSON1 = market.getMarketMap().get("eoseth");
			JSONObject marketJSON2 = market.getMarketMap().get("ethusdt");
			double eosethPrice = new Double(marketJSON1.getString("price"));
			double ethusdtPrice = new Double(marketJSON2.getString("price"));
			price = getRound(eosethPrice * ethusdtPrice * priceRate, priceAccuracy);
			marketAmount = new Double(marketJSON1.getString("amount"));
		} else {
			marketPrice = new Double(marketJSON.getString("price"));
			price = getRound(marketPrice * priceRate, priceAccuracy);
			marketAmount = new Double(marketJSON.getString("amount"));
		}

		double amount = getMinAmount(amountAccuracy);
		if (getRound(marketAmount * amountRate, amountAccuracy) < getMinAmount(amountAccuracy)) {
			amount = getMinAmount(amountAccuracy);
		} else {
			amount = getRound(marketAmount * amountRate, amountAccuracy);
		}
		order.setPrice(price + "");
		order.setAmount(amount + "");

		// System.out.println("||...."+amount);

		String content = null;
		try {
			content = HttpUtil.doPost(target, order);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}

		// System.out.println(order.getDirection()+"：[price:"+order.getPrice()+"，amount:"+order.getAmount()+"]");
		// System.out.println(content);
	}

	public double getRound(double number, int accuracy) {
		int divisor = 100;

		if (accuracy == 1) {
			divisor = 10;
		} else if (accuracy == 2) {
			divisor = 100;
		} else if (accuracy == 3) {
			divisor = 1000;
		} else if (accuracy == 4) {
			divisor = 10000;
		} else if (accuracy == 5) {
			divisor = 100000;
		} else if (accuracy == 6) {
			divisor = 1000000;
		}

		number = (double) Math.round(number * divisor) / divisor;
		return number;
	}

	public double getMinAmount(int accuracy) {
		double minAmount = 0.01;
		if (accuracy == 1) {
			minAmount = 0.1;
		} else if (accuracy == 2) {
			minAmount = 0.01;
		} else if (accuracy == 3) {
			minAmount = 0.001;
		} else if (accuracy == 4) {
			minAmount = 0.0001;
		} else if (accuracy == 5) {
			minAmount = 0.00001;
		} else if (accuracy == 6) {
			minAmount = 0.000001;
		}
		return minAmount;
	}

}