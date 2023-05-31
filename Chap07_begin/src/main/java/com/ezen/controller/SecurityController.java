package com.ezen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

	@GetMapping("/")
	public String index() {
		System.out.println("index 요청입니다.");
		return "index";
	}

	@GetMapping("/member")
	public void forMember() {
		System.out.println("Member 요청입니다.");
	}

	@GetMapping("/manager")
	public void forManager() {
		System.out.println("Manager 요청입니다.");
	}

	@GetMapping("/admin")
	public void forAdmin() {
		System.out.println("Admin 요청입니다.");
	}
	
	@GetMapping("/login")
	public void login() {
		System.out.println("login 요청...");
	}	
	
	@GetMapping("/loginSuccess")
	public void loginSuccess() {
		System.out.println("loginSuccess");
	}
	
	@GetMapping("/accessDenied")
	public void accessDenied() {
		System.out.println("accessDenied");
	}
}



