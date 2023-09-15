package com.jspiders.studentdatabase.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jspiders.studentdatabase.dto.MessageData;
import com.jspiders.studentdatabase.dto.StudentRequest;
import com.jspiders.studentdatabase.dto.StudentResponse;
import com.jspiders.studentdatabase.entity.Student;
import com.jspiders.studentdatabase.service.StudentService;
import com.jspiders.studentdatabase.util.ResponseStructure;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	StudentService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<StudentResponse>> saveStudent(@RequestBody @Valid StudentRequest studentRequest){
		return service.saveStudent(studentRequest);

	}
	
	@PutMapping("/{studentId}")
	public ResponseEntity<ResponseStructure<StudentResponse>> updateStudent(@RequestBody StudentRequest studentRequest,
			@PathVariable int studentId){
				return service.updateStudent(studentRequest, studentId);
	}
	
	@GetMapping("/{studentId}")
	public ResponseEntity<ResponseStructure<StudentResponse>> findById(@PathVariable int studentId){
		return service.findById(studentId);
		
	}
	
	@DeleteMapping("/{studentId}")
	public ResponseEntity<ResponseStructure<StudentResponse>> deleteStudent(@PathVariable int studentId){
		return service.deleteStudent(studentId);
		
	}
	@CrossOrigin
	@GetMapping
	public ResponseEntity<ResponseStructure<List<StudentResponse>>> findAll(){
		return service.findAll();
		
	}
	
//	@GetMapping("/stud/{email}")
//	public ResponseEntity<ResponseStructure<StudentResponse>> findByStudentEmail(@PathVariable String email){
//		return service.findByStudentEmail(email);
//		
//	}
	// this method violates the endpoint rules hence use @Requestparam itself as below
	
	@GetMapping(params = "studentEmail")
	public ResponseEntity<ResponseStructure<StudentResponse>> findByStudentEmail(@RequestParam String studentEmail){
		return service.findByStudentEmail(studentEmail);
		
	}
	
	@GetMapping(params = "studentPhNo")
	public ResponseEntity<ResponseStructure<StudentResponse>> findByStudentPhNo(@RequestParam String studentPhNo){
		return service.findByStudentPhNo(studentPhNo);
		
	}
	
	@GetMapping(params = "studentGrade")
	public ResponseEntity<ResponseStructure<List<StudentResponse>>> findByStudentGrade(@PathVariable String studentGrade){
		return service.findBystudentGrade(studentGrade);
	}
//	
//	@GetMapping("/grade/{studentGrade}")
//	public ResponseEntity<ResponseStructure<List<String>>> getAllEmailByGrade(@RequestParam String studentGrade){
//		return service.getAllEmailsByGrade(studentGrade);
//	}
	
	
	
	@PostMapping("/extract")
	public ResponseEntity<String> extractDataFromExcel(@RequestParam MultipartFile file) throws IOException{
		return service.extractDataFromExcel(file);	
	}
	
	@PostMapping("/write/excel")
	// or @GetMApping()
	public ResponseEntity<String> writeToExcel(@RequestParam String filePath) throws IOException{
		return service.writeToExcel(filePath);
	}
	
	
	@CrossOrigin
	@PostMapping("/mail")
	public ResponseEntity<String> sendmail(@RequestBody MessageData messageData)
	{
		return service.sendMail(messageData);
	}
	
//	@PostMapping("/hii")
//	public void demo(@RequestParam String data)
//	{
//		System.out.println(data);
//	}
	
	@PostMapping("/mime-mail")
	public ResponseEntity<String> sendmimeMessage(@RequestBody MessageData messageData)throws MessagingException
	{
		return service.sendMimeMessage(messageData);
	}
	
	

}
