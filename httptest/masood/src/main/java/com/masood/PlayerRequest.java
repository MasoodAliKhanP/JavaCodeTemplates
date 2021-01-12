package com.masood;

public class PlayerRequest {
	String partnerID;
    String labelID;
    String ecrExternalId;
	public PlayerRequest(String partnerID, String labelID, String ecrExternalId) {
		super();
		this.partnerID = partnerID;
		this.labelID = labelID;
		this.ecrExternalId = ecrExternalId;
	}
    
    
}
