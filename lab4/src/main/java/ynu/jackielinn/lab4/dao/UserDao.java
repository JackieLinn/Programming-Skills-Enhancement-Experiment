package ynu.jackielinn.lab4.dao;

import ynu.jackielinn.lab4.entity.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 用户 DAO：Spring Data JPA 自动实现
 */
public interface UserDao extends IBaseDao<User, Long> {

    // 派生查询：根据方法名自动生成 SQL
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRole(User.Role role);

    // @Query：自定义 JPQL 查询
    @Query("select u from User u where u.username like %:kw% or u.email like %:kw%")
    List<User> search(@Param("kw") String keyword);

    // @Modifying：执行更新/删除操作（非查询）
    @Modifying
    @Transactional
    @Query("update User u set u.password = :pwd where u.id = :id")
    int updatePassword(@Param("id") Long id, @Param("pwd") String newPassword);
}
