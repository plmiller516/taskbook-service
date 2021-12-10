package edu.cpcc.labs.taskbook.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

// This is an example of a Custom Exception...also considered as a checked exception.
public class ApiResourceNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	private void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public ApiResourceNotFoundException(String message) {
		super(message);
		// record the time stamp for this exception..
		setTimestamp(LocalDateTime.now());
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Time Stamp: " + this.getTimestamp());
		builder.append("|");
		builder.append("Message: " + this.getMessage());
		return builder.toString();
	}
 
}
