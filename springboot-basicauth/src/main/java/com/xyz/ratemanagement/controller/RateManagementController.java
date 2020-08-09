package com.xyz.ratemanagement.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.ratemanagement.constants.ApplicationConstants;
import com.xyz.ratemanagement.exception.ErrorMessages;
import com.xyz.ratemanagement.model.Rate;
import com.xyz.ratemanagement.service.RateManagentServiceImpl;

@RestController
@RequestMapping("rms")
public class RateManagementController {

	private static final Logger logger = LogManager.getLogger(RateManagementController.class);

	@Autowired
	public RateManagentServiceImpl rateManagentService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value=ApplicationConstants.SEARCH_RATE, method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchRate(@PathVariable("rateId") Long rateId) {

		logger.info("Start:searchRate " + rateId);
		Rate rateSearch = null;
		try {
			rateSearch = rateManagentService.findByRateIdWithSurCharge(rateId);
			if (rateSearch == null) {
				return new ResponseEntity(
						new Exception(ErrorMessages.RECORD_NOT_FOUND.getErrorMessage()),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Error in SearchRate :" + e.getMessage());
		}
		logger.info("End:searchRate");
		return new ResponseEntity<Rate>(rateSearch, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value=ApplicationConstants.UPDATE_RATE, method=RequestMethod.PUT,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateRate(@PathVariable("rateId") Long rateId, @RequestBody Rate rates) {

		logger.info("Start:updateRate " + rateId);
		Rate rateSearch = null;
		try {
			rateSearch = rateManagentService.findByRateId(rateId);
			
			if (rateSearch == null) {
				return new ResponseEntity(
						new Exception(ErrorMessages.RECORD_NOT_FOUND.getErrorMessage()),
						HttpStatus.NOT_FOUND);
			}
			rateManagentService.save(rates);
		} catch (Exception e) {
			logger.error("Error in updateRate :" + e.getMessage());
		}
		return new ResponseEntity<Rate>(rates, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value=ApplicationConstants.DELETE_RATE, method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteRate(@PathVariable("rateId") Long rateId) throws Exception {

		logger.info("Start:deleteRate " + rateId);
		
		Rate rateSearch = null;
		try {
			rateSearch = rateManagentService.findByRateId(rateId);
			if (rateSearch == null) {
				return new ResponseEntity(
						new Exception(ErrorMessages.RECORD_NOT_FOUND.getErrorMessage()),
						HttpStatus.NOT_FOUND);
			}
			rateManagentService.delete(rateId);
		} catch (Exception e) {
			logger.error("Error in updateRate :" + e.getMessage());
		}
		logger.info("End:deleteRate");
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	
	@RequestMapping(value=ApplicationConstants.ADD_RATE, method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rate> addRate(@Valid @RequestBody Rate rates) {
		logger.info("Start:addRate ");
		try {
			
			rateManagentService.save(rates);
		} catch (Exception e) {
			logger.error("Error in addRate :" + e.getMessage());
		}
		logger.info("End:addRate");
		return new ResponseEntity<Rate>(rates, HttpStatus.OK);
	}
}
