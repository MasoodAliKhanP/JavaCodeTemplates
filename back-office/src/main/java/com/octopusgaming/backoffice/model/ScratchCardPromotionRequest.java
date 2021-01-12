package com.octopusgaming.backoffice.model;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
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
}
