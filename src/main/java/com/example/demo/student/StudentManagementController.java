package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {

	@Autowired
		private StudentService studentService;


	@GetMapping(path = "{studentId}")
	    @PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	    public Student getStudent(@PathVariable("studentId") Integer studentId) {
	    	return studentService.getStudent(studentId);
	    }

	@GetMapping
	    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_ADMIN')")
	    public List<Student> getStudents(){
	    	return studentService.getStudents();
	    }

	@PostMapping
	@PreAuthorize("hasAuthority('student:write')")
	public Student registerNewStudent(@RequestBody Student student) {
		return studentService.registerNewStudent(student);
	}

	@DeleteMapping("/{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void deregisterStudent(@PathVariable Integer studentId) {
		studentService.deregisterStudent(studentId);
	}

	@PutMapping("/{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public Student updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student modifiedStudent) {
		return studentService.updateStudent(studentId, modifiedStudent);
	}
}
