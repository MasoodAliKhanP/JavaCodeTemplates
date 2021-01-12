package com.octopusgaming.backoffice.enums;

public enum ResponseStatus {
	SUCCESS("SUCCESS"),
	FAILED("FAILED"),
	TECHNICAL_ERROR("TECHNICAL_ERROR");
	
	private String status;
	
	ResponseStatus(String status){
		this.status = status;
	}
	
	public String status() {
        return status;
    }
}
