package com.octopusgaming.backoffice.model;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)	
public class ScratchCardConfig {
	private int id;
	private int promotionId;
	
	@NotNull
	private String prizeTypeId;
	@NotNull
	private Integer amount;
	@NotNull
	private Float chancePercentage;
	@NotNull
	private Integer validityHours;
	@NotNull
	private Integer totalPrizes;
	
	private Date createDate;
	private String prizeName;
	private int currentPrizeCount;
}
