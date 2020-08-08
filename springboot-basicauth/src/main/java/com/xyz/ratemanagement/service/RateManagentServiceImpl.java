package com.xyz.ratemanagement.service;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		logger.info("Rate Management Service delete() " + rateId );
		ratesRepository.deleteById(rateId);
	}

	public Rate findByRateId(Long rateId) {
		logger.info("Rate Management Service findByRateId() " + rateId );
		Rate rates = null;
		rates = ratesRepository.findByRateId(rateId);
		return rates;
	}
	
	public Rate findByRateIdWithSurCharge(Long rateId) {
		logger.info("Rate Management Service findByRateIdWithSurCharge() " + rateId);
		Rate rates = null;
		String vatSurChargeValue = null;
		rates = ratesRepository.findByRateId(rateId);
		if (rates != null) {
			vatSurChargeValue = vatSurchargeService.fetchSurchargeDetails();
			if (!StringUtils.isEmpty(vatSurChargeValue)) {
				rates.setSurchargeRate(Long.valueOf(vatSurChargeValue));
			}
		}
		return rates;
	}
}
