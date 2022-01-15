package net.javaguides.springboot.service.interfaces;

import net.javaguides.springboot.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {

    List<UserDto> findAllUsers();

    void blocked(Long id);
    void unblocked(Long id);

}
