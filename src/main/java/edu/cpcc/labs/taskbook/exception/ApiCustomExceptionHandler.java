
package edu.cpcc.labs.taskbook.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


// NOTE: this allows this exception handler to be spread across all controllers...
@ControllerAdvice
public class ApiCustomExceptionHandler extends ResponseEntityExceptionHandler  {
	
	Logger logger = LoggerFactory.getLogger(ApiCustomExceptionHandler.class.getName());	
		
	// Custom Exception...
	@ExceptionHandler(ApiResourceNotFoundException.class)
	protected ResponseEntity<?> handleApiResourceNotFoundException(ApiResourceNotFoundException ex) {

		// log the exception message for troubleshooting...
		logger.error(ex.getMessage());
		
		return new ResponseEntity<Object>(ex.toString(), 
				                         HttpStatus.NOT_FOUND);
	}
	
	// Catch all...
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<?> handleException(Exception ex) {

		// log the exception message for troubleshooting...
		logger.error(ex.getMessage());

		return new ResponseEntity<Object>(ex.toString(), 
				                          HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// NOTE: https://www.baeldung.com/spring-boot-bean-validation
	// NOTE: https://stackoverflow.com/questions/38282298/ambiguous-exceptionhandler-method-mapped-for-class-org-springframework-web-bin
	// Explains how to handle field validation exceptions...
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
															      HttpHeaders headers, 
																  HttpStatus status,
																  WebRequest request) {

		// Java -8 Stream API with a lambda sprinkle snippet...
		List<String> fieldValidationErrors = ex.getBindingResult()
												.getFieldErrors()
												.stream()
												.map(fieldError -> fieldError.getDefaultMessage())
												.collect(Collectors.toList());
		ApiFieldValidationError apiFieldValidationError = new ApiFieldValidationError(HttpStatus.BAD_REQUEST, 
																					  "Whoops !!! API failed input validation.",
																				       ex,
																					   fieldValidationErrors);
		// log the error for troubleshooting ..
		logger.error(apiFieldValidationError.getFieldValidationErrrors().toString());
		logger.error(apiFieldValidationError.getDebugMessage());

		return new ResponseEntity<Object>(apiFieldValidationError, 
				                          new HttpHeaders(), 
				                          apiFieldValidationError.getStatus());
	}
	
	
	// NOTE: for every custom exception create a new handleXXXException method..to create a custom message for that exception..
	

}
