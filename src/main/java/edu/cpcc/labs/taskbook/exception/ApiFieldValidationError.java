package edu.cpcc.labs.taskbook.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiFieldValidationError extends ApiValidationError {
	
	private List<String> fieldValidationErrors;
	
	public ApiFieldValidationError(HttpStatus status, String message, Throwable ex, List<String> fieldValidationErrors) {
		super(status, message, ex);
		this.setFieldValidationErrors(fieldValidationErrors);
	}

	public List<String> getFieldValidationErrrors() {
		return fieldValidationErrors;
	}

	private void setFieldValidationErrors(List<String> fieldValidationErrors) {
		this.fieldValidationErrors = fieldValidationErrors;
	}


	

}
