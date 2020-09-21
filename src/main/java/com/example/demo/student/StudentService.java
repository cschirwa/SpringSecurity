package com.example.demo.student;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public Student getStudent(Integer studentId) {
		return studentRepository.findById(studentId).isPresent() ? studentRepository.findById(studentId).get()
				: new Student("Test");
	}

	public List<Student> getStudents() {
		List<Student> students = new ArrayList<>();
		studentRepository.findAll().forEach(s -> {
			students.add(s);
		});
		return students;
	}

	public Student registerNewStudent(Student student) {
		return studentRepository.save(student);
	}

	
	public void deregisterStudent(Integer studentId) {
		studentRepository.deleteById(studentId);
	}

	
	public Student updateStudent(Integer studentId, Student modifiedStudent) {
		return studentRepository.findById(studentId).map(s -> {
			s.setName(modifiedStudent.getName());
			return studentRepository.save(s);
		})
				.orElseGet(() -> {
					return studentRepository.save(modifiedStudent);
				});
	}
}
