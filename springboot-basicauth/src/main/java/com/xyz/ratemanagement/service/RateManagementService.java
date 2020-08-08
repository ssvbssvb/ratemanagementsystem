package com.xyz.ratemanagement.service;

import com.xyz.ratemanagement.model.Rate;

public interface RateManagementService {

	Rate save(Rate rates);

	void delete(Long rateId);

	Rate findByRateId(Long rateId);

	Rate findByRateIdWithSurCharge(Long rateId);

}
