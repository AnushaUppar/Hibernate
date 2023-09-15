package com.jspiders.studentdatabase.exception;

public class StudentNotFoundByPhNoException extends RuntimeException{
	private String message;
	private String PhNo;
	public StudentNotFoundByPhNoException(String message, String phNo) {
		super();
		this.message = message;
		PhNo = phNo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPhNo() {
		return PhNo;
	}
	public void setPhNo(String phNo) {
		PhNo = phNo;
	}
	

}
