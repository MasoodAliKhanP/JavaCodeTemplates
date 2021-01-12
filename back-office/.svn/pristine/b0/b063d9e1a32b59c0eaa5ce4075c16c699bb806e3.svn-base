package com.octopusgaming.backoffice.dto;

import java.sql.Date;
import java.util.List;

import com.octopusgaming.backoffice.model.MinDepositConfig;
import com.octopusgaming.backoffice.model.ScratchCardConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScratchCardPromotionDTO {
	private int promotionId;
	private String name;
	private Date startDate;
	private Date endDate;
	private int isActive;
	private Date createDate;
	private List<ScratchCardConfig> configs;
	private List<MinDepositConfig> minDepositConfigs;

	public ScratchCardPromotionDTO(String name, Date startDate, Date endDate, List<ScratchCardConfig> configs, List<MinDepositConfig> minDepositConfigs) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.configs = configs;
		this.minDepositConfigs = minDepositConfigs;
	}
}
