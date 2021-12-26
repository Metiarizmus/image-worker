package net.javaguides.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@RequestMapping(value = "/uploadPage", method = RequestMethod.GET)
	public String index() {
		return "upload";
	}

}
