package com.masood;

public class Authorization {
//	{
//	    "grantType": "CLIENT_CREDENTIALS",
//	    "clientDetails": {
//	        "partnerID": "21dukes_chat",
//	        "secretCode": "Ja3AhpsV0jn4toT"
//	    }
//	}
	
	String grantType;
	ClientDetails clientDetails;
	
	public Authorization(String grantType, ClientDetails clientDetails) {
		super();
		this.grantType = grantType;
		this.clientDetails = clientDetails;
	}
	
	
}
