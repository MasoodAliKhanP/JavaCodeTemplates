package com.octopusgaming.backoffice.model;

import java.util.List;

import com.octopusgaming.backoffice.enums.ResponseStatus;

public class ScratchCard {
	private List<ScratchCardConfig> configs;
	private ResponseStatus responseStatus;
	
	public List<ScratchCardConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(List<ScratchCardConfig> configs) {
		this.configs = configs;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	@Override
	public String toString() {
		return "ScratchCard [configs=" + configs + ", responseStatus=" + responseStatus + "]";
	}
	
	
}
