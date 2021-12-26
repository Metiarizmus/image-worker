package net.javaguides.springboot.controller;


import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.service.interfaces.UserRegistrService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	private Logger log = Logger.getLogger(UserRegistrationController.class);

	private UserRegistrService userService;

	public UserRegistrationController(UserRegistrService userService) {
		super();
		this.userService = userService;
	}

	@ModelAttribute("user")
	public UserDto userRegistrationDto() {
		return new UserDto();
	}

	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserDto registrationDto) {
		log.info("save user in db");
		userService.save(registrationDto);
		return "redirect:/registration?success";
	}


}

