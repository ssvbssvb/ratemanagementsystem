package com.xyz.ratemanagement.exception;

public enum ErrorMessages {
	
	RECORD_NOT_FOUND("RateId not found in RMS"),	
	INTERNAL_SEVER_ERROR("Internal server error. Please contact admin"),
	BAD_REQUEST("Bad Request");
	
	private String errorMessage;

	private ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	

}
