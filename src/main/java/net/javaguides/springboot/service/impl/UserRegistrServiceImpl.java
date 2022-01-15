package net.javaguides.springboot.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import net.javaguides.springboot.enums.RolesName;
import net.javaguides.springboot.exceptions.LockedException;
import net.javaguides.springboot.exceptions.UsernameNotFoundException;
import net.javaguides.springboot.service.interfaces.UserRegistrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.dto.UserDto;

@Service
public class UserRegistrServiceImpl implements UserRegistrService {

    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserRegistrServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, LockedException {

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Email or password not correct...");
        }
        if (user.getActive() == 0) {
            throw new LockedException("The admin has blocked you");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
    }


    @Override
    public User save(UserDto registrationDto) {
        User user = new User(registrationDto.getFirstName(),
                registrationDto.getLastName(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()), Collections.singleton((new Role((RolesName.ROLE_USER)))));

        return userRepository.save(user);
    }



}
