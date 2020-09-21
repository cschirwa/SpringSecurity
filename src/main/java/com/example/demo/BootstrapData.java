package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;

@Component
public class BootstrapData {

	
	private StudentRepository svc;
	
	@Autowired
	public BootstrapData(StudentRepository svc) {
		this.svc = svc;
		addStudents();
	}
	public void addStudents() {
		svc.save(new Student("Calvin"));
		svc.save(new Student("Seth"));
		svc.save(new Student("Simba"));
		svc.save(new Student("Nene"));
	}
}
