package com.xyz.ratemanagement.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.ratemanagement.constants.ApplicationConstants;
import com.xyz.ratemanagement.dao.RatesRepository;
import com.xyz.ratemanagement.model.Rate;

@Service
public class RateManagentServiceImpl implements RateManagementService {

	private static final Logger logger = LogManager.getLogger(RateManagentServiceImpl.class);

	@Autowired
	private RatesRepository ratesRepository;

	@Autowired
	private VatSurchargeService vatSurchargeService;

	public Rate save(Rate rates) {
		logger.info("Rate Management Service save()");
		return ratesRepository.save(rates);
	}

	public void delete(Long rateId) {
		logger.info("Rate Management Service delete() " + rateId);
		ratesRepository.deleteById(rateId);
	}

	public Rate findByRateId(Long rateId) {
		logger.info("Rate Management Service findByRateId() " + rateId);
		Rate rates = null;
		rates = ratesRepository.findByRateId(rateId);
		return rates;
	}

	public Rate findByRateIdWithSurCharge(Long rateId) {
		logger.info("Rate Management Service findByRateIdWithSurCharge() " + rateId);
		Rate rates = null;
		Map<String, String> vatSurChargeMap = null;
		String statusCode = null;
		String surchargeRate = ApplicationConstants.SURCHARGE_SERVICE_UNAVALIABLE;
		rates = ratesRepository.findByRateId(rateId);
         
		if (rates != null) {
			try {
			vatSurChargeMap = vatSurchargeService.fetchSurchargeDetails();
			if (vatSurChargeMap.size() == 2) {
				if (vatSurChargeMap.get(ApplicationConstants.STATUS_CODE) != null && vatSurChargeMap.get(ApplicationConstants.SURCHARGE_RATE) != null) {
					statusCode = vatSurChargeMap.get(ApplicationConstants.STATUS_CODE);
					surchargeRate = vatSurChargeMap.get(ApplicationConstants.SURCHARGE_RATE);

					if (!StringUtils.isEmpty(statusCode) && statusCode.equalsIgnoreCase(ApplicationConstants.HTTP_STATUS_200) && !StringUtils.isEmpty(surchargeRate)) {
						rates.setSurchargeRate(surchargeRate);
					} else {
						rates.setSurchargeRate(ApplicationConstants.SURCHARGE_SERVICE_UNAVALIABLE);
					}
				}
			} 
		}
		 catch(Exception e) {
			 rates.setSurchargeRate(ApplicationConstants.SURCHARGE_SERVICE_UNAVALIABLE);
			 logger.error("error while calling vatsubcharge service" + e.getMessage());
		 }
			rates.setSurchargeRate(ApplicationConstants.SURCHARGE_SERVICE_UNAVALIABLE);
		}
		
		return rates;
	}
}
