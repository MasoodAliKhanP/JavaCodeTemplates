// Generated by delombok at Tue Jan 12 12:45:24 IST 2021
package com.octopusgaming.backoffice.model;

import java.sql.Date;

public class ScratchCardWinner {
	private String name;
	private String externalPlayerId;
	private String prizeTypeId;
	private String prizeName;
	private Float amount;
	private Date createDate;

	@java.lang.SuppressWarnings("all")
	public String getName() {
		return this.name;
	}

	@java.lang.SuppressWarnings("all")
	public String getExternalPlayerId() {
		return this.externalPlayerId;
	}

	@java.lang.SuppressWarnings("all")
	public String getPrizeTypeId() {
		return this.prizeTypeId;
	}

	@java.lang.SuppressWarnings("all")
	public String getPrizeName() {
		return this.prizeName;
	}

	@java.lang.SuppressWarnings("all")
	public Float getAmount() {
		return this.amount;
	}

	@java.lang.SuppressWarnings("all")
	public Date getCreateDate() {
		return this.createDate;
	}

	@java.lang.SuppressWarnings("all")
	public void setName(final String name) {
		this.name = name;
	}

	@java.lang.SuppressWarnings("all")
	public void setExternalPlayerId(final String externalPlayerId) {
		this.externalPlayerId = externalPlayerId;
	}

	@java.lang.SuppressWarnings("all")
	public void setPrizeTypeId(final String prizeTypeId) {
		this.prizeTypeId = prizeTypeId;
	}

	@java.lang.SuppressWarnings("all")
	public void setPrizeName(final String prizeName) {
		this.prizeName = prizeName;
	}

	@java.lang.SuppressWarnings("all")
	public void setAmount(final Float amount) {
		this.amount = amount;
	}

	@java.lang.SuppressWarnings("all")
	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof ScratchCardWinner)) return false;
		final ScratchCardWinner other = (ScratchCardWinner) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$name = this.getName();
		final java.lang.Object other$name = other.getName();
		if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
		final java.lang.Object this$externalPlayerId = this.getExternalPlayerId();
		final java.lang.Object other$externalPlayerId = other.getExternalPlayerId();
		if (this$externalPlayerId == null ? other$externalPlayerId != null : !this$externalPlayerId.equals(other$externalPlayerId)) return false;
		final java.lang.Object this$prizeTypeId = this.getPrizeTypeId();
		final java.lang.Object other$prizeTypeId = other.getPrizeTypeId();
		if (this$prizeTypeId == null ? other$prizeTypeId != null : !this$prizeTypeId.equals(other$prizeTypeId)) return false;
		final java.lang.Object this$prizeName = this.getPrizeName();
		final java.lang.Object other$prizeName = other.getPrizeName();
		if (this$prizeName == null ? other$prizeName != null : !this$prizeName.equals(other$prizeName)) return false;
		final java.lang.Object this$amount = this.getAmount();
		final java.lang.Object other$amount = other.getAmount();
		if (this$amount == null ? other$amount != null : !this$amount.equals(other$amount)) return false;
		final java.lang.Object this$createDate = this.getCreateDate();
		final java.lang.Object other$createDate = other.getCreateDate();
		if (this$createDate == null ? other$createDate != null : !this$createDate.equals(other$createDate)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof ScratchCardWinner;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $name = this.getName();
		result = result * PRIME + ($name == null ? 43 : $name.hashCode());
		final java.lang.Object $externalPlayerId = this.getExternalPlayerId();
		result = result * PRIME + ($externalPlayerId == null ? 43 : $externalPlayerId.hashCode());
		final java.lang.Object $prizeTypeId = this.getPrizeTypeId();
		result = result * PRIME + ($prizeTypeId == null ? 43 : $prizeTypeId.hashCode());
		final java.lang.Object $prizeName = this.getPrizeName();
		result = result * PRIME + ($prizeName == null ? 43 : $prizeName.hashCode());
		final java.lang.Object $amount = this.getAmount();
		result = result * PRIME + ($amount == null ? 43 : $amount.hashCode());
		final java.lang.Object $createDate = this.getCreateDate();
		result = result * PRIME + ($createDate == null ? 43 : $createDate.hashCode());
		return result;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public java.lang.String toString() {
		return "ScratchCardWinner(name=" + this.getName() + ", externalPlayerId=" + this.getExternalPlayerId() + ", prizeTypeId=" + this.getPrizeTypeId() + ", prizeName=" + this.getPrizeName() + ", amount=" + this.getAmount() + ", createDate=" + this.getCreateDate() + ")";
	}

	@java.lang.SuppressWarnings("all")
	public ScratchCardWinner() {
	}
}
