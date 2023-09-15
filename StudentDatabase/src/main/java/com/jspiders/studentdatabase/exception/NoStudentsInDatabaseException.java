package com.jspiders.studentdatabase.exception;

public class NoStudentsInDatabaseException extends RuntimeException {
	private String message;
	

	public NoStudentsInDatabaseException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

//	public void setMessage(String message) {
//		this.message = message;
//	}
	

}
