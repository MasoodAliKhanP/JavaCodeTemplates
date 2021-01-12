package com.octopusgaming.backoffice.service;

import org.springframework.http.ResponseEntity;

import com.octopusgaming.backoffice.model.ScratchCardPromotionRequest;
import com.octopusgaming.backoffice.model.ScratchCardPromotionResponse;
import com.octopusgaming.backoffice.model.ScratchCardWinnersRequest;
import com.octopusgaming.backoffice.model.ScratchCardWinnersResponse;

public interface BackOfficeService {

	ResponseEntity<?> login(String userName, String password);
	
	ResponseEntity<?> logout();
	
	ResponseEntity<ScratchCardPromotionResponse> getScratchCardPromotion();
	
	void setScratchCardPromotion(ScratchCardPromotionRequest request);
	
	void deletePromotion(int promotionId);
	
	void deleteAllPromotions();
	
	ResponseEntity<ScratchCardWinnersResponse> winners(ScratchCardWinnersRequest request);
}
