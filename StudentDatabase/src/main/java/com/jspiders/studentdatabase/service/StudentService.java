package com.jspiders.studentdatabase.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.jspiders.studentdatabase.dto.MessageData;
import com.jspiders.studentdatabase.dto.StudentRequest;
import com.jspiders.studentdatabase.dto.StudentResponse;
import com.jspiders.studentdatabase.util.ResponseStructure;

public interface StudentService {
//	public ResponseEntity<ResponseStructure<Student>> saveStudent(Student Student);
//	public ResponseEntity<ResponseStructure<Student>> updateStudent(Student student, int studentId);
//	public ResponseEntity<ResponseStructure<Student>> findById(int studentId);
//	public ResponseEntity<ResponseStructure<Student>> deleteStudent(int studentId);
//	public ResponseEntity<ResponseStructure<List<Student>>> findAll();
	
	public ResponseEntity<ResponseStructure<StudentResponse>> saveStudent(StudentRequest studentRequest);
	public ResponseEntity<ResponseStructure<StudentResponse>> updateStudent(StudentRequest studentRequest, int studentId);
	public ResponseEntity<ResponseStructure<StudentResponse>> findById(int studentId);
	public ResponseEntity<ResponseStructure<StudentResponse>> deleteStudent(int studentId);
	public ResponseEntity<ResponseStructure<List<StudentResponse>>> findAll();
	public ResponseEntity<ResponseStructure<StudentResponse>> findByStudentEmail(String studentEmail);
	public ResponseEntity<ResponseStructure<StudentResponse>> findByStudentPhNo(String studentPhNo);
	public ResponseEntity<ResponseStructure<List<StudentResponse>>> findBystudentGrade(String studentGrade);
	//*********************** Query ***************************************
	
	public ResponseEntity<ResponseStructure<List<String>>> getAllEmailsByGrade(String grade);
	
	//********** to read excel sheets *********************************************
	public ResponseEntity<String> extractDataFromExcel(MultipartFile file)throws IOException;
	
	//  ************** write data to excel sheet **************************
	public ResponseEntity<String> writeToExcel(String filePath) throws IOException;
	
	public ResponseEntity<String> sendMail(MessageData messageData);
	public ResponseEntity<String> sendMimeMessage(MessageData messageData)throws MessagingException;


}
