package ynu.jackielinn.lab5;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ynu.jackielinn.lab5.dao.*;
import ynu.jackielinn.lab5.dto.EnrollmentView;
import ynu.jackielinn.lab5.entity.*;

import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class EnrollmentDaoTest {

    @Resource
    private EnrollmentDao enrollmentDao;
    @Resource
    private UserDao userDao;
    @Resource
    private CourseDao courseDao;

    @Test
    public void insertManyEnrollments() {
        List<User> students = userDao.findByRole(User.Role.STUDENT);
        List<Course> courses = courseDao.findAll();

        if (students.isEmpty() || courses.isEmpty()) {
            System.out.println("请先运行UserDaoTest.insertManyUsers 和 CourseDaoTest.insertManyCourses");
            return;
        }

        User s1 = students.get(0);
        User s2 = students.size() > 1 ? students.get(1) : students.get(0);

        for (int i = 0; i < Math.min(5, courses.size()); i++) {
            Course c = courses.get(i);

            if (!enrollmentDao.existsByStudentIdAndCourseId(s1.getId(), c.getId())) {
                Enrollment e = Enrollment.builder()
                        .student(s1)
                        .course(c)
                        .createTime(LocalDateTime.now())
                        .build();
                enrollmentDao.save(e);
            }

            if (i % 2 == 0 && !enrollmentDao.existsByStudentIdAndCourseId(s2.getId(), c.getId())) {
                Enrollment e2 = Enrollment.builder()
                        .student(s2)
                        .course(c)
                        .createTime(LocalDateTime.now())
                        .build();
                enrollmentDao.save(e2);
            }
        }
    }

    @Test
    public void queryByStudent() {
        List<User> students = userDao.findByRole(User.Role.STUDENT);
        if (students.isEmpty()) return;

        Long sid = students.get(0).getId();
        List<Enrollment> list = enrollmentDao.findByStudentId(sid);
        System.out.println("学生ID=" + sid + " 选课数=" + list.size());

        List<EnrollmentView> views = list.stream().map(e ->
                EnrollmentView.builder()
                        .enrollmentId(e.getId())
                        .studentId(e.getStudent().getId())
                        .studentUsername(e.getStudent().getUsername())
                        .courseId(e.getCourse().getId())
                        .courseName(e.getCourse().getName())
                        .createTime(e.getCreateTime())
                        .build()
        ).toList();

        views.forEach(System.out::println);
    }

    @Test
    public void deleteOneEnrollment() {
        List<Enrollment> all = enrollmentDao.findAll();
        if (!all.isEmpty()) {
            enrollmentDao.deleteById(all.get(all.size() - 1).getId());
        }
    }
}
