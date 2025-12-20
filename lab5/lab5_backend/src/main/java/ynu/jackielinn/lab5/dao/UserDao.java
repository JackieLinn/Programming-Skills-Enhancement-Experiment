package ynu.jackielinn.lab5.dao;

import ynu.jackielinn.lab5.entity.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface UserDao extends IBaseDao<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByRole(User.Role role);

    @Query("select u from User u where u.role = :role and (u.username like %:kw% or u.email like %:kw%)")
    List<User> searchByRole(@Param("role") User.Role role, @Param("kw") String keyword);

    @Modifying
    @Transactional
    @Query("update User u set u.email = :email where u.id = :id")
    int updateEmail(@Param("id") Long id, @Param("email") String email);
}
