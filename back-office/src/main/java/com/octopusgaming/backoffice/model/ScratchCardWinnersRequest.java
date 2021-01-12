package com.octopusgaming.backoffice.model;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ScratchCardWinnersRequest {
    @NotNull(message = "StartDate is mandatory")
	private Date startDate;
    
    @NotNull(message = "EndDate is mandatory")
	private Date endDate;
	
	private Boolean onlyLegAndJackpot;
}
