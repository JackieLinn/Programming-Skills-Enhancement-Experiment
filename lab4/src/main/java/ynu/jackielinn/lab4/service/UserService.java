package ynu.jackielinn.lab4.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import ynu.jackielinn.lab4.dao.UserDao;
import ynu.jackielinn.lab4.entity.User;

import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Resource
    private UserDao userDao;

    public User register(User u) {
        u.setCreateTime(LocalDateTime.now());
        if (u.getRole() == null) u.setRole(User.Role.CUSTOMER);
        return userDao.save(u);
    }

    public int changePassword(Long id, String newPwd) {
        return userDao.updatePassword(id, newPwd);
    }

    public Optional<User> find(Long id) {
        return userDao.findById(id);
    }

    public List<User> search(String kw) {
        return userDao.search(kw);
    }

    public void delete(Long id) {
        userDao.deleteById(id);
    }
}
