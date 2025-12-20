package ynu.jackielinn.lab4.dao;

import ynu.jackielinn.lab4.entity.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserDao extends IBaseDao<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByRole(User.Role role);

    @Query("select u from User u where u.username like %:kw% or u.email like %:kw%")
    List<User> search(@Param("kw") String keyword);

    @Modifying
    @Transactional
    @Query("update User u set u.password = :pwd where u.id = :id")
    int updatePassword(@Param("id") Long id, @Param("pwd") String newPassword);
}
