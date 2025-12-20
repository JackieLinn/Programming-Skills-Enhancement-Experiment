package ynu.jackielinn.lab5.service;

import ynu.jackielinn.lab5.dao.UserDao;
import ynu.jackielinn.lab5.entity.User;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class AuthService {

    @Resource
    private UserDao userDao;

    public boolean adminLogin(String username, String password) {
        return userDao.findByUsername(username)
                .filter(u -> u.getRole() == User.Role.ADMIN)
                .map(u -> u.getPassword().equals(password))
                .orElse(false);
    }

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
