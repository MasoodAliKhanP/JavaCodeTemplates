package com.octopusgaming.backoffice.model;

public class WinConfigResponse {
	private String prizeTypeId;
	private int amount;
	private String prizeName;
	public String getPrizeTypeId() {
		return prizeTypeId;
	}
	public void setPrizeTypeId(String prizeTypeId) {
		this.prizeTypeId = prizeTypeId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	@Override
	public String toString() {
		return "WinConfigResponse [prizeTypeId=" + prizeTypeId + ", amount=" + amount + ", prizeName=" + prizeName + "]";
	}
	
	
}
