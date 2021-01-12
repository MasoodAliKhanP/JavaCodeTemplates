// Generated by delombok at Tue Jan 12 12:45:24 IST 2021
package com.octopusgaming.backoffice.model;

import java.sql.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ScratchCardPromotionRequest {
	@NotNull(message = "Name is mandatory")
	private String name;
	@NotNull(message = "StartDate is mandatory")
	private Date startDate;
	@NotNull(message = "EndDate is mandatory")
	private Date endDate;
	@NotNull(message = "Configs is mandatory")
	@Valid
	private List<ScratchCardConfig> configs;
	@NotNull
	@Valid
	private List<MinDepositConfig> minDepositConfigs;

	@java.lang.SuppressWarnings("all")
	public ScratchCardPromotionRequest() {
	}

	@java.lang.SuppressWarnings("all")
	public String getName() {
		return this.name;
	}

	@java.lang.SuppressWarnings("all")
	public Date getStartDate() {
		return this.startDate;
	}

	@java.lang.SuppressWarnings("all")
	public Date getEndDate() {
		return this.endDate;
	}

	@java.lang.SuppressWarnings("all")
	public List<ScratchCardConfig> getConfigs() {
		return this.configs;
	}

	@java.lang.SuppressWarnings("all")
	public List<MinDepositConfig> getMinDepositConfigs() {
		return this.minDepositConfigs;
	}

	@java.lang.SuppressWarnings("all")
	public void setName(final String name) {
		this.name = name;
	}

	@java.lang.SuppressWarnings("all")
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@java.lang.SuppressWarnings("all")
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@java.lang.SuppressWarnings("all")
	public void setConfigs(final List<ScratchCardConfig> configs) {
		this.configs = configs;
	}

	@java.lang.SuppressWarnings("all")
	public void setMinDepositConfigs(final List<MinDepositConfig> minDepositConfigs) {
		this.minDepositConfigs = minDepositConfigs;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof ScratchCardPromotionRequest)) return false;
		final ScratchCardPromotionRequest other = (ScratchCardPromotionRequest) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$name = this.getName();
		final java.lang.Object other$name = other.getName();
		if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
		final java.lang.Object this$startDate = this.getStartDate();
		final java.lang.Object other$startDate = other.getStartDate();
		if (this$startDate == null ? other$startDate != null : !this$startDate.equals(other$startDate)) return false;
		final java.lang.Object this$endDate = this.getEndDate();
		final java.lang.Object other$endDate = other.getEndDate();
		if (this$endDate == null ? other$endDate != null : !this$endDate.equals(other$endDate)) return false;
		final java.lang.Object this$configs = this.getConfigs();
		final java.lang.Object other$configs = other.getConfigs();
		if (this$configs == null ? other$configs != null : !this$configs.equals(other$configs)) return false;
		final java.lang.Object this$minDepositConfigs = this.getMinDepositConfigs();
		final java.lang.Object other$minDepositConfigs = other.getMinDepositConfigs();
		if (this$minDepositConfigs == null ? other$minDepositConfigs != null : !this$minDepositConfigs.equals(other$minDepositConfigs)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof ScratchCardPromotionRequest;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $name = this.getName();
		result = result * PRIME + ($name == null ? 43 : $name.hashCode());
		final java.lang.Object $startDate = this.getStartDate();
		result = result * PRIME + ($startDate == null ? 43 : $startDate.hashCode());
		final java.lang.Object $endDate = this.getEndDate();
		result = result * PRIME + ($endDate == null ? 43 : $endDate.hashCode());
		final java.lang.Object $configs = this.getConfigs();
		result = result * PRIME + ($configs == null ? 43 : $configs.hashCode());
		final java.lang.Object $minDepositConfigs = this.getMinDepositConfigs();
		result = result * PRIME + ($minDepositConfigs == null ? 43 : $minDepositConfigs.hashCode());
		return result;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public java.lang.String toString() {
		return "ScratchCardPromotionRequest(name=" + this.getName() + ", startDate=" + this.getStartDate() + ", endDate=" + this.getEndDate() + ", configs=" + this.getConfigs() + ", minDepositConfigs=" + this.getMinDepositConfigs() + ")";
	}
}