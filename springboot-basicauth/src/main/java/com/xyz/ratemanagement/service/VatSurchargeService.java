package com.xyz.ratemanagement.service;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
	public String fetchSurchargeDetails() {

		logger.info("Start : fetchSurchargeDetails() ");
	
		//RestTemplate restTemplate = new RestTemplate();
		JsonNode root = null;
		
		String result = restTemplate.getForObject(ApplicationConstants.VAT_URL, String.class);
		ObjectMapper mapper = new ObjectMapper();
		try {
			root = mapper.readTree(result);
		} catch (IOException e) {
			logger.error("error while calling vatsubcharge service" + e.getMessage());

		}
		JsonNode rate = root.path("surchargeRate");
		logger.info("End : fetchSurchargeDetails() ");
		return rate.asText();
	}

	@SuppressWarnings("unused")
	private String fetchSurchargeDetails_Fallback() {
		logger.info("Inside : fetchSurchargeDetails_Fallback() ");
		return ("No Response from VAT surcharge service");
	}
	
	
}
