package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.controller.ImageController;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.enums.RolesName;
import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.interfaces.AdminService;
import net.javaguides.springboot.utils.DtoConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoConvert dtoConvert;

    @Override
    public List<UserDto> findAllUsers() {

        List<User> users = userRepository.findAll(findAllBy(RolesName.ROLE_USER));
        log.info("get all user by ROLE_USER ");
        List<UserDto> userDtos = new ArrayList<>();
        for (User q : users) {
            userDtos.add(dtoConvert.convertToDto(q));
        }

        return userDtos;
    }


    public static Specification<User> findAllBy(RolesName input) {
        return new Specification<User>() {
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<User,Role> userProd = root.join("roles");
                return cb.equal(userProd.get("name"), input);
            }
        };
    }

    @Override
    public void blocked(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.info("blocked user with id :: " + id);
            user.get().setActive(0);
            userRepository.save(user.get());
        }
    }

    @Override
    public void unblocked(Long id) {
       Optional<User> user = userRepository.findById(id);
       if (user.isPresent()) {
           log.info("unblocked user with id :: " + id);
           user.get().setActive(1);
           userRepository.save(user.get());
       }
    }

}
