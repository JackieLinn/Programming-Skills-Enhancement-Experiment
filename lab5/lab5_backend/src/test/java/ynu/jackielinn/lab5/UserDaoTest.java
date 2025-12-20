package ynu.jackielinn.lab5;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ynu.jackielinn.lab5.dao.UserDao;
import ynu.jackielinn.lab5.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class UserDaoTest {

    @Resource
    private UserDao userDao;

    @Test
    public void insertUsers() {

        // 只插入一个管理员
        if (userDao.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password("123456")
                    .email("admin@lab5.com")
                    .role(User.Role.ADMIN)
                    .createTime(LocalDateTime.now())
                    .build();
            userDao.save(admin);
        }

        // 插入学生
        for (int i = 1; i <= 5; i++) {
            String name = "student" + i;
            if (userDao.findByUsername(name).isEmpty()) {
                User stu = User.builder()
                        .username(name)
                        .password("123456")
                        .email(name + "@qq.com")
                        .role(User.Role.STUDENT)
                        .createTime(LocalDateTime.now())
                        .build();
                userDao.save(stu);
            }
        }
    }

    @Test
    public void queryStudents() {
        List<User> students = userDao.findByRole(User.Role.STUDENT);
        students.forEach(System.out::println);
    }

    @Test
    public void updateEmail() {
        User student = userDao.findByUsername("student1").orElseThrow();
        userDao.updateEmail(student.getId(), "student1_new@qq.com");
        System.out.println(userDao.findById(student.getId()).orElse(null));
    }

    @Test
    public void deleteStudent() {
        userDao.findByUsername("student5")
                .ifPresent(u -> userDao.deleteById(u.getId()));
    }
}
