package com.xyz.ratemanagement.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xyz.ratemanagement.constants.ApplicationConstants;

@Service
public class VatSurchargeService {

	private static final Logger logger = LogManager.getLogger(VatSurchargeService.class);
	
	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "fetchSurchargeDetails_Fallback")
	public Map<String, String> fetchSurchargeDetails() {
		
		Map<String,String> vatMap= new HashMap<String,String>();
		logger.info("Start : fetchSurchargeDetails() ");
		JsonNode root = null;
		
		String result = restTemplate.getForObject(ApplicationConstants.VAT_URL, String.class);
		ResponseEntity<String> statusCode = restTemplate.postForEntity(ApplicationConstants.VAT_URL, null, String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			root = mapper.readTree(result);
		} catch (IOException e) {
			logger.error("error while calling vatsubcharge service statusCode : " + statusCode + " " + e.getMessage());
		}
		JsonNode rate = root.path("surchargeRate");
		
		// Setting response code and data 
		//Service is not avalaible
		//vatMap.put(ApplicationConstants.STATUS_CODE, "501");
		
		vatMap.put(ApplicationConstants.STATUS_CODE, statusCode.getStatusCode().toString());
		vatMap.put(ApplicationConstants.SURCHARGE_RATE, rate.asText());
		logger.info("End : fetchSurchargeDetails() ");
		return vatMap;
	}

	@SuppressWarnings("unused")
	private String fetchSurchargeDetails_Fallback() {
		logger.info("Inside : fetchSurchargeDetails_Fallback() ");
		return ("No Response from VAT surcharge service");
	}
	
	
}
