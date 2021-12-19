package edu.cpcc.labs.taskbook.model;

public enum TaskStatus {	
	// NOTE: these are defaulted to numeric weights, with the starting value of 0 and in increments of 1 in the order
	// that are placed in. For example, OPEN = 0, WORKED = 1, COMPLETE = 2, PENDING = 3 and CANCELLED = 4
	OPEN,
	WORKED,
	COMPLETE,
	PENDING,
	CANCELLED;
}
