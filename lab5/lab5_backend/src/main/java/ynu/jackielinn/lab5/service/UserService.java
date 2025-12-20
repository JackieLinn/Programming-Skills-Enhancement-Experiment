package ynu.jackielinn.lab5.service;

import ynu.jackielinn.lab5.dao.UserDao;
import ynu.jackielinn.lab5.entity.User;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Resource
    private UserDao userDao;

    public List<User> listStudents() {
        return userDao.findByRole(User.Role.STUDENT);
    }

    public List<User> searchStudents(String keyword) {
        return userDao.searchByRole(User.Role.STUDENT, keyword);
    }

    public User updateStudent(Long id, String username, String password, String email) {
        User u = userDao.findById(id).orElseThrow();

        if (u.getRole() != User.Role.STUDENT) {
            throw new IllegalArgumentException("只能维护学生账号");
        }

        if (username != null && !username.isBlank()) u.setUsername(username);
        if (password != null && !password.isBlank()) u.setPassword(password);
        if (email != null && !email.isBlank()) u.setEmail(email);

        return userDao.save(u);
    }

    public int updateEmail(Long id, String email) {
        return userDao.updateEmail(id, email);
    }

    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }
}
