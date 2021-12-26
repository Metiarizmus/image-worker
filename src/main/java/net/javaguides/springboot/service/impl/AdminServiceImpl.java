package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.interfaces.AdminService;
import net.javaguides.springboot.utils.DtoConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoConvert dtoConvert;

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAllByRoles("ROLE_USER");

        List<UserDto> userDtos = new ArrayList<>();
        for (User q : users) {
            userDtos.add(dtoConvert.convertToDto(q));
        }

        return userDtos;
    }

    @Override
    public void blocked(Long id) {
        userRepository.blockedUser(id);
    }

    @Override
    public void unblocked(Long id) {
        userRepository.unBlockedUser(id);
    }


}
