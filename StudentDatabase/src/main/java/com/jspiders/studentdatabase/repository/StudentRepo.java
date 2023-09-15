package com.jspiders.studentdatabase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jspiders.studentdatabase.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {
	
	public Student findByStudentEmail(String email);
	
	public Student findByStudentPhNo(String phno);
	
	public List<Student> findByStudentGrade(String studentGrade);
	@Query("select s.studentEmail from Student s where studentGrade=?1")
	public List<String> getAllEmailsByGrade(String grade);
	
	
	
	
	

}
