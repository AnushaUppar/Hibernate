package com.jspiders.studentdatabase.serviceimpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.jspiders.studentdatabase.dto.MessageData;
import com.jspiders.studentdatabase.dto.StudentRequest;
import com.jspiders.studentdatabase.dto.StudentResponse;
import com.jspiders.studentdatabase.entity.Student;
import com.jspiders.studentdatabase.exception.NoStudentsInDatabaseException;
import com.jspiders.studentdatabase.exception.StudentNotFoundByEmailException;
import com.jspiders.studentdatabase.exception.StudentNotFoundByIdException;
import com.jspiders.studentdatabase.exception.StudentNotFoundByPhNoException;
import com.jspiders.studentdatabase.repository.StudentRepo;
import com.jspiders.studentdatabase.service.StudentService;
import com.jspiders.studentdatabase.util.ResponseStructure;


@Service
public class StudentServiceimpl implements StudentService {
	
	@Autowired
	private StudentRepo repo;
	
	@Autowired
	private JavaMailSender javaMailSender;

  //**************** Save student without DTO **********************************
//	@Override
//	public ResponseEntity<ResponseStructure<Student>> saveStudent(Student student) {
//		Student student2 = repo.save(student);
//		ResponseStructure<Student> structure = new ResponseStructure<>();
//		structure.setStatus(HttpStatus.CREATED.value());
//		structure.setMessage("Student data saved successfully");
//		structure.setData(student2);
//		return new ResponseEntity<ResponseStructure<Student>> (structure, HttpStatus.CREATED) ;
//	}
	
	
	//**************** Save student with DTO **********************************
	
	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> saveStudent(StudentRequest studentRequest) {
		Student student = new Student();
		// set data from StudentRequest to database(student)
		student.setStudentName(studentRequest.getStudentName());
		student.setStudentPhNo(studentRequest.getStudentPhNo());
		student.setStudentEmail(studentRequest.getStudentEmail());
		student.setStudentGrade(studentRequest.getStudentGrade());
		student.setStudentPassword(studentRequest.getStudentPassword());
		Student student2 = repo.save(student);
		//set the data from database(student2) to StudentResponse
		StudentResponse studentResponse = new StudentResponse();
		studentResponse.setStudentId(student2.getStudentId());
		studentResponse.setStudentName(student2.getStudentName());
		studentResponse.setStudentGrade(student2.getStudentGrade());
		
		ResponseStructure<StudentResponse> structure = new ResponseStructure<>();
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setMessage("Student data saved successfully");
		structure.setData(studentResponse);
		return new ResponseEntity<ResponseStructure<StudentResponse>> (structure, HttpStatus.CREATED) ;
	}

	
	
	//**************** Update student without DTO **********************************
//	@Override
//	public ResponseEntity<ResponseStructure<Student>> updateStudent(Student student, int studentId) {
//		Optional<Student> optional = repo.findById(studentId);
//		if(optional.isPresent()) {
//			//Student student2 = optional.get();
//			student.setStudentId(studentId);
//			//student.setStudentId(optional.get().getStudentId());
//			//student.setStudentId(student2.getStudentId());
//			student = repo.save(student);
//			ResponseStructure<Student> structure = new ResponseStructure<>();
//			structure.setStatus(HttpStatus.OK.value());
//			structure.setMessage("Student data updated sucessfully");
//			structure.setData(student);
//			return new ResponseEntity<ResponseStructure<Student>> (structure, HttpStatus.OK) ;	
//		}
//		else {
//			throw new StudentNotFoundByIdException("Failed to update the student ", studentId);
//		}
//	}
	
	// **************** Update Student with DTO *****************************
	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> updateStudent(StudentRequest studentRequest, int studentId) {
		Optional<Student> optional = repo.findById(studentId);
		if(optional.isPresent()) {
			Student student2 = optional.get();
			student2.setStudentId(studentId);
			student2.setStudentName(studentRequest.getStudentName());
			student2.setStudentPhNo(studentRequest.getStudentPhNo());
			student2.setStudentEmail(studentRequest.getStudentEmail());
			student2.setStudentGrade(studentRequest.getStudentGrade());
			Student updatedStudent = repo.save(student2);
			
			StudentResponse studnetResponse = new StudentResponse();
			studnetResponse.setStudentId(updatedStudent.getStudentId());
			studnetResponse.setStudentName(updatedStudent.getStudentName());
			studnetResponse.setStudentGrade(updatedStudent.getStudentGrade());
			
			ResponseStructure<StudentResponse> structure = new ResponseStructure<>();
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Student data updated successfully");
			structure.setData(studnetResponse);
			return new ResponseEntity<ResponseStructure<StudentResponse>> (structure, HttpStatus.OK) ;	
		}
		else {
			throw new StudentNotFoundByIdException("Failed to update the student ", studentId);
		}
	}

	
	//**************** Find student by ID without DTO **********************************
//	@Override
//	public ResponseEntity<ResponseStructure<Student>> findById(int studentId) {
//		Optional<Student> optional = repo.findById(studentId);
//		if(optional.isPresent()) {
//			Student student2 = optional.get();
//			ResponseStructure<Student> structure = new ResponseStructure<>();
//			structure.setStatus(HttpStatus.FOUND.value());
//			structure.setMessage("Student is found");
//			structure.setData(student2);
//			
//			return new ResponseEntity<ResponseStructure<Student>> (structure, HttpStatus.FOUND);
//			
//		}
//		else {
//			
//			throw new StudentNotFoundByIdException("Failed to find the student ", studentId);
//		}
//		
//	}
	
	
	//**************** Find Student By ID with DTO **********************************
	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> findById(int studentId) {
		Optional<Student> optional = repo.findById(studentId);
		if(optional.isPresent()) {
			Student student2 = optional.get();
			StudentResponse studentResponse = new StudentResponse();
			studentResponse.setStudentId(student2.getStudentId());
			studentResponse.setStudentName(student2.getStudentName());
			studentResponse.setStudentGrade(student2.getStudentGrade());;
			
			ResponseStructure<StudentResponse> structure = new ResponseStructure<>();
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Student is found");
			structure.setData(studentResponse);
			
			return new ResponseEntity<ResponseStructure<StudentResponse>> (structure, HttpStatus.FOUND);
			
		}
		else {
			
			throw new StudentNotFoundByIdException("Failed to find the student ", studentId);
		}	
	}
	
	//****************Delete student  without DTO **********************************
//	@Override
//	public ResponseEntity<ResponseStructure<Student>> deleteStudent(int studentId) {
//		Optional<Student> optional = repo.findById(studentId);
//		if(optional.isPresent()) {
//			Student student2 = optional.get();
//			repo.deleteById(studentId);
//			ResponseStructure<Student> structure = new ResponseStructure<>();
//			structure.setStatus(HttpStatus.OK.value());
//			structure.setMessage("Student with Id "+studentId +" deleted sucessfully");
//			structure.setData(student2);
//			return new ResponseEntity<ResponseStructure<Student>> (structure, HttpStatus.OK);
//			
//		}
//		else {
//			 throw new StudentNotFoundByIdException("Failed to delete the student ", studentId);
//		}
//		
//	}
	
	//****************Delete student with DTO **********************************
		@Override
		public ResponseEntity<ResponseStructure<StudentResponse>> deleteStudent(int studentId) {
			Optional<Student> optional = repo.findById(studentId);
			if(optional.isPresent()) {
				Student student2 = optional.get();
				repo.deleteById(studentId);
				StudentResponse studentResponse = new StudentResponse();
				studentResponse.setStudentId(student2.getStudentId());
				studentResponse.setStudentName(student2.getStudentName());
				studentResponse.setStudentGrade(student2.getStudentGrade());
				ResponseStructure<StudentResponse> structure = new ResponseStructure<>();
				structure.setStatus(HttpStatus.OK.value());
				structure.setMessage("Student with Id "+studentId +" deleted sucessfully");
				structure.setData(studentResponse);
				return new ResponseEntity<ResponseStructure<StudentResponse>> (structure, HttpStatus.OK);
				
			}
			else {
				 throw new StudentNotFoundByIdException("Failed to delete the student ", studentId);
			}
			
		}

	
	
	//**************** Find All without DTO **********************************
//	@Override
//	public ResponseEntity<ResponseStructure<List<Student>>> findAll() {
//		List<Student> list = repo.findAll();
//		if(!list.isEmpty()) {
//			ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();
//			structure.setStatus(HttpStatus.OK.value());
//			structure.setMessage("Below list of students are found");
//			structure.setData(list);
//			return new ResponseEntity<ResponseStructure<List<Student>>> (structure , HttpStatus.FOUND);
//			
//		}
//		return null;
//	}

	
	//**************** FIND  ALL() with DTO **********************************
	
	@Override
	public ResponseEntity<ResponseStructure<List<StudentResponse>>> findAll() {
		List<Student> list = repo.findAll();
		if(!list.isEmpty()) {
			
			List<StudentResponse> students = new ArrayList<StudentResponse>();
			
					for(Student stds : list) {
						StudentResponse studentResponse = new StudentResponse();
						studentResponse.setStudentId(stds.getStudentId());
						studentResponse.setStudentName(stds.getStudentName());
						studentResponse.setStudentGrade(stds.getStudentGrade());
					    students.add( studentResponse);
			}
			
//			for(StudentResponse slist: students)
//			{
//				System.out.println(slist);
//			}
			ResponseStructure<List<StudentResponse>> structure = new ResponseStructure<List<StudentResponse>>();
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Below list of students are found");
			structure.setData(students);
			return new ResponseEntity<ResponseStructure<List<StudentResponse>>> (structure , HttpStatus.FOUND);
			
		}
		else {
			throw new NoStudentsInDatabaseException("There are No students in DataBase");
		}
	}
	
	//*********************** CUSTOM REPOSITORY METHODS *****************************
	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> findByStudentEmail(String studentEmail) {
		Student student = repo.findByStudentEmail(studentEmail);
		if(student!=null) {
			StudentResponse studentResponse = new StudentResponse();
			studentResponse.setStudentId(student.getStudentId());
			studentResponse.setStudentName(student.getStudentName());
			studentResponse.setStudentGrade(student.getStudentGrade());
			
			ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Student found based on Email");
			structure.setData(studentResponse);
			
			return new ResponseEntity<ResponseStructure<StudentResponse>>(structure, HttpStatus.FOUND);
		}
		else {
			
			throw new StudentNotFoundByEmailException("Failed to find the student", studentEmail);
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> findByStudentPhNo(String studentPhNo) {
		Student student = repo.findByStudentPhNo(studentPhNo);
		if(student!=null) {
			StudentResponse studentResponse = new StudentResponse();
			studentResponse.setStudentId(student.getStudentId());
			studentResponse.setStudentName(student.getStudentName());
			studentResponse.setStudentGrade(student.getStudentGrade());
			
			ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Student found based on phone number");
			structure.setData(studentResponse);
			return new ResponseEntity<ResponseStructure<StudentResponse>>(structure,HttpStatus.FOUND);
		}
		else {
			throw new StudentNotFoundByPhNoException("Failed to find the student", studentPhNo);
		}
	
	}

//	@Override
//	public ResponseEntity<ResponseStructure<List<String>>> getAllEmailsByGrade(String grade) {
//		List<String> list = repo.getAllEmailsByGrade(grade);
//		if(list!=null) {
//			ResponseStructure<List<String>> structure = new ResponseStructure<List<String>>();
//			structure.setStatus(HttpStatus.FOUND.value());
//			structure.setMessage("Following students are found by grade "+grade);
//			structure.setData(list);
//			return new ResponseEntity<ResponseStructure<List<String>>> (structure, HttpStatus.FOUND);
//		}
//		return null;
//	}



	@Override
	public ResponseEntity<ResponseStructure<List<StudentResponse>>> findBystudentGrade(String studentGrade) {
		List<Student> list = repo.findByStudentGrade(studentGrade);
		if(list!=null) {
			List<StudentResponse> responselist = new ArrayList<StudentResponse>();
			for(Student stds:list) {
				StudentResponse response = new StudentResponse();
				response.setStudentName(stds.getStudentName());
				response.setStudentId(stds.getStudentId());
				response.setStudentGrade(stds.getStudentGrade());
				responselist.add(response);	
			}
			ResponseStructure<List<StudentResponse>> structure = new ResponseStructure<List<StudentResponse>>();
			structure.setData(responselist);
			structure.setMessage("Following students are found by grade "+studentGrade);
			structure.setStatus(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<StudentResponse>>>(structure, HttpStatus.FOUND);
		}
		return null;
	}



	@Override
	public ResponseEntity<ResponseStructure<List<String>>> getAllEmailsByGrade(String grade) {
		List<String> emaillist = repo.getAllEmailsByGrade(grade);
		if(emaillist!=null) {
			ResponseStructure<List<String>> structure = new ResponseStructure<List<String>>();
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Below list of email are found by requested grade");
			structure.setData(emaillist);
			return new ResponseEntity<ResponseStructure<List<String>>>(structure,HttpStatus.FOUND);
		}
		
		return null;
	}

	//**************************************** Read the data from excel sheet *************************************************
	@Override
	public ResponseEntity<String> extractDataFromExcel(MultipartFile file) throws IOException  {
		//System.out.println("Entered into method");
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		for(Sheet sheet : workbook) {
			for(Row row : sheet) {
				System.out.println("in row");	
		if(row.getRowNum()>0) {
			if(row!=null) {
			String name = row.getCell(0).getStringCellValue();
			String email = row.getCell(1).getStringCellValue();
			long phoneNumber = (long) row.getCell(2).getNumericCellValue();
			String grade = row.getCell(3).getStringCellValue();
			String password = row.getCell(4).getStringCellValue();
			System.out.println("Student detail ");
			System.out.println(name+", "+email+", "+phoneNumber+", "+grade+", "+password);
			Student student = new Student();
			student.setStudentName(name);
			student.setStudentEmail(email);
			student.setStudentPhNo(phoneNumber);
			student.setStudentPassword(password);
			student.setStudentGrade(grade);
			repo.save(student);		
			}
		}				
	}
}
		workbook.close();
		return new ResponseEntity<String>("Data read from excel sheet", HttpStatus.OK);
	}

// ******************** write to excel sheet ************************************
	@Override
	public ResponseEntity<String> writeToExcel(String filePath) throws IOException {
		List<Student> studentslist = repo.findAll();
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("StudentId");
		header.createCell(1).setCellValue("StudentName");
		header.createCell(2).setCellValue("StudentPhNo");
		header.createCell(3).setCellValue("StudentEmail");
		header.createCell(4).setCellValue("StudentGrade");
		header.createCell(5).setCellValue("StudentPassword");
		int rownum=1;
		for(Student student : studentslist ) {
			Row row = sheet.createRow(rownum++);
			row.createCell(0).setCellValue(student.getStudentId());
			row.createCell(1).setCellValue(student.getStudentName());
			row.createCell(2).setCellValue(student.getStudentPhNo());
			row.createCell(3).setCellValue(student.getStudentEmail());
			row.createCell(4).setCellValue(student.getStudentGrade());
			row.createCell(5).setCellValue(student.getStudentPassword());
		}
		FileOutputStream outputStream = new FileOutputStream(filePath);
		workbook.write(outputStream);
			
		workbook.close();
		return new ResponseEntity<String>("Data transfered to excel sheet ", HttpStatus.OK);
	}
	
	
	
	//*******************WRITE TO CSV FILE ************************//
	
	public ResponseEntity<String> exportToCsv(HttpServletResponse response) throws IOException {
		  response.setContentType("text/csv");
	       
	      String headerKey = "Content-Disposition";
	      String headerValue = "attachment; filename=users_"+ ".csv";

		  response.setHeader(headerKey, headerValue);
	       
	      List<Student> listUsers = repo.findAll();

	      CsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
	      String[] csvHeader = {"Student ID","StudentName", "Student E-mail", "Student Phone Number", "Student Grade", "Student Password"};
	      String[] nameMapping = {"Studentid","StudentName", "StudentEmail", "StudentPhNo", "StudentGrade" , "StudentPassword"};
	       
	      csvWriter.writeHeader(csvHeader);
	       
	      for (Student student : listUsers) {
	          csvWriter.write(student, nameMapping);
	      }       
	      csvWriter.close();
	      return new ResponseEntity<String>("WRITTEN SUCCESSFULLY",HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> sendMail(MessageData messageData) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		String[] mails=messageData.getTo();
		message.setTo(messageData.getTo());
		message.setSubject(messageData.getSubject());
		message.setCc(mails);
		
	   // message.setText(messageData.getText());
		message.setText(messageData.getText()
			+"\n\nThanks and Regards"+"\n"+messageData.getSenderName()+"\n"+messageData.getSenderAddress());
		message.setSentDate(new Date());
		javaMailSender.send(message);
		
			return new ResponseEntity<String>("MAIL SEND SUCCESSFULLY",HttpStatus.OK);
	}
	@Override
	public ResponseEntity<String> sendMimeMessage(MessageData messageData) throws MessagingException
	{
		
		MimeMessage mime = javaMailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mime,true);
		message.setTo(messageData.getTo());
		message.setSubject(messageData.getSubject());
		String emailBody=messageData.getText()+"<br><br><h4>THANKS AND REGARDS </h4></br>"+"<h4>"
		+messageData.getSenderName()+"</h4><br>"+"<h4>"+messageData.getSenderAddress()+"</h4>"+
				"<img src=\"https://jspiders.com/_nuxt/img/logo_jspiders.3b552d0.png\" width=\"250\">";
		
		message.setText(emailBody,true);
		message.setSentDate(new Date());
		
		javaMailSender.send(mime);
		
		return new ResponseEntity<String>("mime message sent successfulyyy!!!!!!",HttpStatus.OK);
		
	}

	

}
