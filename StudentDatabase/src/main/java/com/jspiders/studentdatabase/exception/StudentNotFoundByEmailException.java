package com.jspiders.studentdatabase.exception;

public class StudentNotFoundByEmailException extends RuntimeException {
	private String message;
	private String email;
	
	
	public StudentNotFoundByEmailException(String message, String email) {
		super();
		this.message = message;
		this.email = email;
	}
	public String getMessage() {
		return message;
	}
//	public void setMessage(String message) {
//		this.message = message;
//	}
	public String getEmail() {
		return email;
	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
	

}
