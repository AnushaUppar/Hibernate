package com.jspiders.studentdatabase.exception;

public class StudentNotFoundByIdException extends RuntimeException{

	private String message;
	private int studentId;

	public StudentNotFoundByIdException(String message, int studentId) {
		super();
		this.message = message;
		this.studentId = studentId;
	}

	public String getMessage() {
		return message;
	}
	
	public int getStudentId() {
		return studentId;
	}

//	public void setMessage(String message) {
//		this.message = message;
//	} no need of setter since parametirized constructor is present
	

}
