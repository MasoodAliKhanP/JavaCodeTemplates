package com.octopusgaming.backoffice.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScratchCardPromotion {
	private int promotionId;
	private String name;
	private Date startDate;
	private Date endDate;
	private Date createDate;
	private List<ScratchCardConfig> configs;
	private List<MinDepositConfig> minDepositConfigs;
}
