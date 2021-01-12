package com.octopusgaming.backoffice.dao;

import java.util.List;

import com.octopusgaming.backoffice.dto.ScratchCardPromotionDTO;
import com.octopusgaming.backoffice.model.Administrator;
import com.octopusgaming.backoffice.model.ScratchCardWinner;
import com.octopusgaming.backoffice.model.ScratchCardWinnersRequest;

public interface BackOfficeDao {
	Administrator getUser(String userName, String password);
	
	ScratchCardPromotionDTO getScratchCardPromotion();
	
	int setScratchCardPromotion(ScratchCardPromotionDTO scratchCardPromotionDTO);
	
	void deleteAllPromotions();
	
	void deletePromotion(int promotionId);
	
	void inactivateActivePromotion();
	
	List<ScratchCardWinner>  winners(ScratchCardWinnersRequest request);
}
