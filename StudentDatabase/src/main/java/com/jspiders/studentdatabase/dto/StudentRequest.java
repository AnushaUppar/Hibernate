package com.jspiders.studentdatabase.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class StudentRequest {
	@NotNull(message = "Student name cannot be null")
	private String studentName;
	@Min(value = 6000000000l, message= "Phone number cannot start below 6")
	@Max(value = 9999999999l, message= "Phone number cannot be above 9999999999")
	private long studentPhNo;
	@NotBlank(message = "Student email cannot be blank")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[g][m][a][i][l]+.[c][o][m]", message = "invalid email--Should be in the extension of '@gmail.com' ")
	private String studentEmail;
	private String studentGrade;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "8 characters mandatory(1 upperCase,1 lowerCase,1 special Character,1 number)")
	private String studentPassword;
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public long getStudentPhNo() {
		return studentPhNo;
	}
	public void setStudentPhNo(long studentPhNo) {
		this.studentPhNo = studentPhNo;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	public String getStudentGrade() {
		return studentGrade;
	}
	public void setStudentGrade(String studentGrade) {
		this.studentGrade = studentGrade;
	}
	public String getStudentPassword() {
		return studentPassword;
	}
	public void setStudentpassword(String studentPassword) {
		this.studentPassword = studentPassword;
	}
	
	
	
	
	

}
