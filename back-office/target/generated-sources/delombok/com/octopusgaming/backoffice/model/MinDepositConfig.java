// Generated by delombok at Tue Jan 12 12:45:24 IST 2021
package com.octopusgaming.backoffice.model;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MinDepositConfig {
	private Integer promotionId;
	@NotNull
	private String currency;
	@NotNull
	private Float commonAmount;
	@NotNull
	private Float legendaryAmount;
	@NotNull
	private Float jackpotAmount;

	@java.lang.SuppressWarnings("all")
	public Integer getPromotionId() {
		return this.promotionId;
	}

	@java.lang.SuppressWarnings("all")
	public String getCurrency() {
		return this.currency;
	}

	@java.lang.SuppressWarnings("all")
	public Float getCommonAmount() {
		return this.commonAmount;
	}

	@java.lang.SuppressWarnings("all")
	public Float getLegendaryAmount() {
		return this.legendaryAmount;
	}

	@java.lang.SuppressWarnings("all")
	public Float getJackpotAmount() {
		return this.jackpotAmount;
	}

	@java.lang.SuppressWarnings("all")
	public void setPromotionId(final Integer promotionId) {
		this.promotionId = promotionId;
	}

	@java.lang.SuppressWarnings("all")
	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	@java.lang.SuppressWarnings("all")
	public void setCommonAmount(final Float commonAmount) {
		this.commonAmount = commonAmount;
	}

	@java.lang.SuppressWarnings("all")
	public void setLegendaryAmount(final Float legendaryAmount) {
		this.legendaryAmount = legendaryAmount;
	}

	@java.lang.SuppressWarnings("all")
	public void setJackpotAmount(final Float jackpotAmount) {
		this.jackpotAmount = jackpotAmount;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof MinDepositConfig)) return false;
		final MinDepositConfig other = (MinDepositConfig) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$promotionId = this.getPromotionId();
		final java.lang.Object other$promotionId = other.getPromotionId();
		if (this$promotionId == null ? other$promotionId != null : !this$promotionId.equals(other$promotionId)) return false;
		final java.lang.Object this$currency = this.getCurrency();
		final java.lang.Object other$currency = other.getCurrency();
		if (this$currency == null ? other$currency != null : !this$currency.equals(other$currency)) return false;
		final java.lang.Object this$commonAmount = this.getCommonAmount();
		final java.lang.Object other$commonAmount = other.getCommonAmount();
		if (this$commonAmount == null ? other$commonAmount != null : !this$commonAmount.equals(other$commonAmount)) return false;
		final java.lang.Object this$legendaryAmount = this.getLegendaryAmount();
		final java.lang.Object other$legendaryAmount = other.getLegendaryAmount();
		if (this$legendaryAmount == null ? other$legendaryAmount != null : !this$legendaryAmount.equals(other$legendaryAmount)) return false;
		final java.lang.Object this$jackpotAmount = this.getJackpotAmount();
		final java.lang.Object other$jackpotAmount = other.getJackpotAmount();
		if (this$jackpotAmount == null ? other$jackpotAmount != null : !this$jackpotAmount.equals(other$jackpotAmount)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof MinDepositConfig;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $promotionId = this.getPromotionId();
		result = result * PRIME + ($promotionId == null ? 43 : $promotionId.hashCode());
		final java.lang.Object $currency = this.getCurrency();
		result = result * PRIME + ($currency == null ? 43 : $currency.hashCode());
		final java.lang.Object $commonAmount = this.getCommonAmount();
		result = result * PRIME + ($commonAmount == null ? 43 : $commonAmount.hashCode());
		final java.lang.Object $legendaryAmount = this.getLegendaryAmount();
		result = result * PRIME + ($legendaryAmount == null ? 43 : $legendaryAmount.hashCode());
		final java.lang.Object $jackpotAmount = this.getJackpotAmount();
		result = result * PRIME + ($jackpotAmount == null ? 43 : $jackpotAmount.hashCode());
		return result;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public java.lang.String toString() {
		return "MinDepositConfig(promotionId=" + this.getPromotionId() + ", currency=" + this.getCurrency() + ", commonAmount=" + this.getCommonAmount() + ", legendaryAmount=" + this.getLegendaryAmount() + ", jackpotAmount=" + this.getJackpotAmount() + ")";
	}

	@java.lang.SuppressWarnings("all")
	public MinDepositConfig() {
	}
}