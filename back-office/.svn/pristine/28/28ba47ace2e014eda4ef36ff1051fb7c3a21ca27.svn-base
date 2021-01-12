package com.octopusgaming.backoffice.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
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
}
