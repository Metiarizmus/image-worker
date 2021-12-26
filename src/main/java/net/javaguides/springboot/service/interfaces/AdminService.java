package net.javaguides.springboot.service.interfaces;

import net.javaguides.springboot.dto.UserDto;

import java.util.List;

public interface AdminService {
    List<UserDto> findAll();
    void blocked(Long id);
    void unblocked(Long id);

}
