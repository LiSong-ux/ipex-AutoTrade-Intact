package ai.turbochain.ipex.entity;

import java.util.Date;

public class TradeParameter {
	
	private int id;
	
	private String coin;
	
	private String baseCoin;
	
	private int priceAccuracy;
	
	private int amountAccuracy;
	
	private Date updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCoin() {
		return coin;
	}

	public void setCoin(String coin) {
		this.coin = coin;
	}

	public String getBaseCoin() {
		return baseCoin;
	}

	public void setBaseCoin(String baseCoin) {
		this.baseCoin = baseCoin;
	}

	public int getPriceAccuracy() {
		return priceAccuracy;
	}

	public void setPriceAccuracy(int priceAccuracy) {
		this.priceAccuracy = priceAccuracy;
	}

	public int getAmountAccuracy() {
		return amountAccuracy;
	}

	public void setAmountAccuracy(int amountAccuracy) {
		this.amountAccuracy = amountAccuracy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "TradeParameter [id=" + id + ", coin=" + coin + ", baseCoin=" + baseCoin + ", priceAccuracy="
				+ priceAccuracy + ", amountAccuracy=" + amountAccuracy + ", updateTime=" + updateTime + "]";
	}
	
	

}
