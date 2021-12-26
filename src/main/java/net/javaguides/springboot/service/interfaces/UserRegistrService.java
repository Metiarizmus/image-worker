package net.javaguides.springboot.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.dto.UserDto;

import java.util.List;

public interface UserRegistrService extends UserDetailsService{
	User save(UserDto registrationDto);

}
