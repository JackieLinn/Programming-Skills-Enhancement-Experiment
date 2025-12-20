package ynu.jackielinn.lab4;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ynu.jackielinn.lab4.dao.UserDao;
import ynu.jackielinn.lab4.entity.User;

import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class UserDaoTest {

    @Resource
    private UserDao userDao;

    @Test
    public void addUsers() {
        for (int i = 1; i <= 5; i++) {
            User u = new User();
            u.setUsername("user" + i);
            u.setPassword("pwd" + i);
            u.setEmail("user" + i + "@qq.com");
            u.setRole(i == 1 ? User.Role.ADMIN : User.Role.CUSTOMER);
            u.setCreateTime(LocalDateTime.now());
            userDao.save(u);
        }
    }

    @Test
    public void queryUsers() {
        List<User> all = userDao.findAll();
        all.forEach(System.out::println);
    }

    @Test
    public void updatePassword() {
        User u = userDao.findByUsername("user2").orElseThrow();
        userDao.updatePassword(u.getId(), "newPwd222");
        System.out.println(userDao.findById(u.getId()).orElse(null));
    }

    @Test
    public void customSearch() {
        List<User> res = userDao.search("user");
        res.forEach(System.out::println);
    }

    @Test
    public void deleteUser() {
        User u = userDao.findByUsername("user5").orElseThrow();
        userDao.deleteById(u.getId());
    }
}
