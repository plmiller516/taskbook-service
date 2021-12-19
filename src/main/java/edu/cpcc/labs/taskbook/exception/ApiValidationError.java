package edu.cpcc.labs.taskbook.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiValidationError {
		
   private HttpStatus status;
   private String message;
   private String debugMessage;
   
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
   private LocalDateTime timestamp;
  
   public ApiValidationError(HttpStatus status, String message, Throwable ex) {
       this.setStatus(status);
       this.setMessage(message);
       this.setDebugMessage(ex.getLocalizedMessage());
       this.setTimestamp(LocalDateTime.now());
   }

	public HttpStatus getStatus() {
		return status;
	}
	
	private void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	private void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getMessage() {
		return message;
	}
	
	private void setMessage(String message) {
		this.message = message;
	}
	
	public String getDebugMessage() {
		return debugMessage;
	}
	
	private void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}
	

}
