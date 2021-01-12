package com.octopusgaming.backoffice.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.octopusgaming.backoffice.model.ScratchCardPromotionRequest;
import com.octopusgaming.backoffice.model.ScratchCardPromotionResponse;
import com.octopusgaming.backoffice.model.ScratchCardWinnersRequest;
import com.octopusgaming.backoffice.model.ScratchCardWinnersResponse;
import com.octopusgaming.backoffice.model.User;
import com.octopusgaming.backoffice.service.BackOfficeService;

//@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 4800, allowCredentials = "false", allowedHeaders = {
//		"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization" })
@RestController
@RequestMapping(value = "/backoffice")
public class BackOfficeController {

	@Autowired
	BackOfficeService backOfficeService;

	private final Log log = LogFactory.getLog(getClass());

	@GetMapping("/")
	public ResponseEntity<?> getSession(HttpSession session) {
		log.debug("--------------------- get session ---------------------" + session.getId());
		return new ResponseEntity<>("", new HttpHeaders(), HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/hello")
	public void hello() {
		log.debug("------- hello --------------");
	}

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		log.debug("name: " + user.getUserName() + " Password: " + user.getPassword());
		return backOfficeService.login(user.getUserName(), user.getPassword());
	}

	@PostMapping(value = "/logout")
	public ResponseEntity<?> logout(@RequestBody User user) {
		log.debug("name: " + user.getUserName() + " Password: " + user.getPassword());
		return backOfficeService.logout();
	}
	
	@GetMapping(value = "/promotion")
	public ResponseEntity<ScratchCardPromotionResponse> getConfig() {
		log.debug("getConfig");
		return backOfficeService.getScratchCardPromotion();
	}
	
	@PostMapping(value = "/promotion")
	public void setConfig(@Valid @RequestBody ScratchCardPromotionRequest scratchCardConfigRequest) {
		log.debug(scratchCardConfigRequest.toString());
		backOfficeService.setScratchCardPromotion(scratchCardConfigRequest);
	}
	
	@DeleteMapping(value = "/promotion/{id}")
	public void deleteConfig(@PathVariable("id") int promotionId) {
		log.debug("deleteConfig");
		backOfficeService.deletePromotion(promotionId);
	}
	
	@DeleteMapping(value = "/allPromotions")
	public void deletePromotions() {
		log.debug("deleteConfig");
		backOfficeService.deleteAllPromotions();
	}
	
	@PostMapping(value = "/winners")
	public ResponseEntity<ScratchCardWinnersResponse> winners(@Valid @RequestBody ScratchCardWinnersRequest request) {
		log.debug("winners");
		return backOfficeService.winners(request);
	}
}