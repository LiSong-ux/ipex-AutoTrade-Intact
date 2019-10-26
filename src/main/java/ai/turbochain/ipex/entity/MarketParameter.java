package ai.turbochain.ipex.entity;

import java.util.Date;

public class MarketParameter {
	
	private int id;
	
	private String coin;
	
	private String baseCoin;
	
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "MarketParameter [id=" + id + ", coin=" + coin + ", baseCoin=" + baseCoin + ", updateTime=" + updateTime
				+ "]";
	}
	
	

}
