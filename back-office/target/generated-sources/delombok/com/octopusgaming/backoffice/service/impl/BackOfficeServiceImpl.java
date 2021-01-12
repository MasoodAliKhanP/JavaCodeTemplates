package com.octopusgaming.backoffice.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.octopusgaming.backoffice.dao.BackOfficeDao;
import com.octopusgaming.backoffice.dto.ScratchCardPromotionDTO;
import com.octopusgaming.backoffice.enums.ResponseStatus;
import com.octopusgaming.backoffice.model.Administrator;
import com.octopusgaming.backoffice.model.ScratchCardConfig;
import com.octopusgaming.backoffice.model.ScratchCardPromotion;
import com.octopusgaming.backoffice.model.ScratchCardPromotionRequest;
import com.octopusgaming.backoffice.model.ScratchCardPromotionResponse;
import com.octopusgaming.backoffice.model.ScratchCardWinner;
import com.octopusgaming.backoffice.model.ScratchCardWinnersRequest;
import com.octopusgaming.backoffice.model.ScratchCardWinnersResponse;
import com.octopusgaming.backoffice.service.BackOfficeService;
import com.octopusgaming.backoffice.utils.EncodeUtil;

@Service
public class BackOfficeServiceImpl implements BackOfficeService {

	private final Log log = LogFactory.getLog(getClass());

	private static final Float SUM_PERCENTAGE = 100.0f;

	@Autowired
	BackOfficeDao backOfficeDao;

//	@Autowired
//	PrizeWin prizeWin;

	@Override
	public ResponseEntity<?> login(String userName, String password) {
		Map<String, String> response = new HashMap<>();
		try {
			Administrator admin = backOfficeDao.getUser(userName, EncodeUtil.sha256(password));
			log.debug(admin.toString());
		} catch (Exception e) {
			log.error(e);
			response.put("Response", "Failed");
			response.put("Description", "Username/Password Not found.");
			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin:", "*");

		response.put("Response", "Success");
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> logout() {
		return new ResponseEntity<>("", new HttpHeaders(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ScratchCardPromotionResponse> getScratchCardPromotion() {
		ScratchCardPromotionResponse response = new ScratchCardPromotionResponse();
		try {
			ScratchCardPromotionDTO promotionDto = backOfficeDao.getScratchCardPromotion();
			ScratchCardPromotion promotion;

			if (promotionDto == null) {
				promotion = new ScratchCardPromotion();
			} else {
				promotion = new ScratchCardPromotion(promotionDto.getPromotionId(), promotionDto.getName(),
						promotionDto.getStartDate(), promotionDto.getEndDate(), promotionDto.getCreateDate(),
						promotionDto.getConfigs(), promotionDto.getMinDepositConfigs());
			}
			response.setPromotion(promotion);
			response.setResponseStatus(ResponseStatus.SUCCESS);
		} catch (Exception e) {
			log.error(e);
			response.setResponseStatus(ResponseStatus.FAILED);
		}
		log.debug("Scratch card: " + response);
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}

	@Override
	public void setScratchCardPromotion(ScratchCardPromotionRequest request) {
		Float sum = 0.0f;
		for (ScratchCardConfig config : request.getConfigs()) {
			sum += config.getChancePercentage();
		}
		Float truncatedSum = BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_UP).floatValue();

		if (Float.compare(truncatedSum, SUM_PERCENTAGE) != 0) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Chance percent sum is not 100.", null);
		}

		ScratchCardPromotionDTO promotionDto = new ScratchCardPromotionDTO(request.getName(), request.getStartDate(),
				request.getEndDate(), request.getConfigs(), request.getMinDepositConfigs());

		backOfficeDao.inactivateActivePromotion();
		int result = backOfficeDao.setScratchCardPromotion(promotionDto);
		if (result == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request.", null);
		}

	}

	@Override
	public void deletePromotion(int promotionId) {
		backOfficeDao.deletePromotion(promotionId);
	}

	@Override
	public void deleteAllPromotions() {
		backOfficeDao.deleteAllPromotions();
	}

	@Override
	public ResponseEntity<ScratchCardWinnersResponse> winners(ScratchCardWinnersRequest request) {
		List<ScratchCardWinner> winners = backOfficeDao.winners(request);
		ScratchCardWinnersResponse response = new ScratchCardWinnersResponse();
		response.setWinners(winners);

		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}

}
