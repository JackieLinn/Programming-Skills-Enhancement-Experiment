package ynu.jackielinn.lab5.service;

import ynu.jackielinn.lab5.dao.UserDao;
import ynu.jackielinn.lab5.entity.User;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

/**
 * 认证服务：管理员登录 + 学生注册
 */
@Service
@Transactional
public class AuthService {

    @Resource
    private UserDao userDao;

    /**
     * 管理员登录验证
     * 使用 Optional 链式处理：查找用户 -> 过滤角色 -> 验证密码
     */
    public boolean adminLogin(String username, String password) {
        return userDao.findByUsername(username)
                .filter(u -> u.getRole() == User.Role.ADMIN)  // 必须是管理员
                .map(u -> u.getPassword().equals(password))   // 验证密码
                .orElse(false);
    }

    /**
     * 学生注册（使用 Builder 模式创建对象）
     */
    public User registerStudent(String username, String password, String email) {
        User u = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(User.Role.STUDENT)
                .createTime(LocalDateTime.now())
                .build();
        return userDao.save(u);
    }
}
