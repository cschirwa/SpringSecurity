package com.example.demo.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CourseController {
	
	@GetMapping("/login")
	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	public String loginView() {
		return "/login";
	}
	
	@GetMapping("courses")
	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	public String coursesView() {
		return "courses";
	}

}
