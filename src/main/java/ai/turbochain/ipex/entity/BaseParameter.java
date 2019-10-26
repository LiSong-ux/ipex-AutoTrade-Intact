package ai.turbochain.ipex.entity;

import java.util.Date;

import com.alibaba.fastjson.JSON;

public class BaseParameter {
	
	private int id;
	
	private int cycleLength;
	
	private String inquireTarget;
	
	private String tradeTarget;
	
	private double priceRate;
	
	private double amountRate;
	
	private Date updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCycleLength() {
		return cycleLength;
	}

	public void setCycleLength(int cycleLength) {
		this.cycleLength = cycleLength;
	}

	public String getInquireTarget() {
		return inquireTarget;
	}

	public void setInquireTarget(String inquireTarget) {
		this.inquireTarget = inquireTarget;
	}

	public String getTradeTarget() {
		return tradeTarget;
	}
	
	public Target getTargetObj() {
		Target targetObj = JSON.parseObject(tradeTarget, Target.class);
		return targetObj;
	}

	public void setTradeTarget(String tradeTarget) {
		this.tradeTarget = tradeTarget;
	}

	public double getPriceRate() {
		return priceRate;
	}

	public void setPriceRate(double priceRate) {
		this.priceRate = priceRate;
	}

	public double getAmountRate() {
		return amountRate;
	}

	public void setAmountRate(double amountRate) {
		this.amountRate = amountRate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "BaseParameter [id=" + id + ", cycleLength=" + cycleLength + ", inquireTarget=" + inquireTarget
				+ ", tradeTarget=" + tradeTarget + ", priceRate=" + priceRate + ", amountRate=" + amountRate
				+ ", updateTime=" + updateTime + "]";
	}
	
	
	
}
