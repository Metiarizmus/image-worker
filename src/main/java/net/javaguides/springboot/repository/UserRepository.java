package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query(value = "SELECT * from user join users_roles on user.id=users_roles.user_id join role on users_roles.role_id=role.id where role.name =:role", nativeQuery = true)
    List<User> findAllByRoles(@Param("role") String role);

    @Modifying
    @Query("update User u set u.active = 0 WHERE u.id = ?1")
    @Transactional
    void blockedUser(Long id);

    @Modifying
    @Query("update User u set u.active = 1 WHERE u.id = ?1")
    @Transactional
    void unBlockedUser(Long id);

}
