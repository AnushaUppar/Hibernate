package edu.jsp.student_management_system.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class MainInsert {
	
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("anu");
	EntityManager manager=f actory.createEntityManager();
	EntityTransaction transaction = manager.getTransaction();
	
	public void saveStudent(Student student)
	{
		transaction.begin();
		manager.persist(student);
		transaction.commit();
	}
	
	public void updateStudent(Student student)
	{
		transaction.begin();
		manager.merge(student);
		transaction.commit();
		
	}
	
	public List<Student> getStudents(){
		
		Query query= manager.createQuery("select s from Student s");
		
		List<Student> students=query.getResultList();
		return students;
		
	}
	
	
	public void deleteStudent(Student student)
	{
		transaction.begin();
		manager.remove(student);
		transaction.commit();
		
	}
	
	
	public Student findStudent(int id)
	{
		return manager.find(Student.class, id);
	}
	
	public static void main(String[] args) {
		
		Student student = new Student();
//		student.setStudentId(104);
//		student.setStudentName("MOUNESH");
//		student.setStudentPhno(9732633923l);
		MainInsert main = new MainInsert();
//		main.saveStudent(student);
		
		List<Student> students=main.getStudents();
		for(Student student2:students)
		{
			System.out.println(student2);
		}
//		main.updateStudent(student);
//		Student s = main.findStudent(102);
//		main.deleteStudent(s0);
		
		
	}

}
