package ynu.jackielinn.lab5;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ynu.jackielinn.lab5.dao.CourseDao;
import ynu.jackielinn.lab5.entity.Course;

import java.util.List;

@SpringBootTest
public class CourseDaoTest {

    @Resource
    private CourseDao courseDao;

    @Test
    public void insertCourses() {

        for (int i = 1; i <= 5; i++) {
            String name = "course" + i;
            boolean exists = courseDao.findByName(name).isPresent();
            if (!exists) {
                Course c = Course.builder()
                        .name(name)
                        .teacher("teacher")
                        .credit(3)
                        .build();
                courseDao.save(c);
            }
        }
    }

    @Test
    public void queryByTeacher() {
        List<Course> list = courseDao.findByTeacher("teacher");
        list.forEach(System.out::println);
    }

    @Test
    public void updateTeacher() {
        Course course = courseDao.findByName("course1").orElseThrow();
        courseDao.updateTeacher(course.getId(), "teacher");
        System.out.println(courseDao.findById(course.getId()).orElse(null));
    }

    @Test
    public void deleteCourse() {
        courseDao.findByName("course5")
                .ifPresent(c -> courseDao.deleteById(c.getId()));
    }
}
