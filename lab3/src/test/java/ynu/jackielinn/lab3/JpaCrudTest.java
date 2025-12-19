package ynu.jackielinn.lab3;

import org.junit.jupiter.api.*;
import ynu.jackielinn.lab3.dao.impl.CourseDao;
import ynu.jackielinn.lab3.dao.impl.EnrollmentDao;
import ynu.jackielinn.lab3.dao.impl.StudentDao;
import ynu.jackielinn.lab3.entity.Course;
import ynu.jackielinn.lab3.entity.Enrollment;
import ynu.jackielinn.lab3.entity.Student;
import ynu.jackielinn.lab3.util.JPAUtil;

import java.time.LocalDateTime;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JpaCrudTest {

    static StudentDao studentDao = new StudentDao();
    static CourseDao courseDao = new CourseDao();
    static EnrollmentDao enrollmentDao = new EnrollmentDao();

    static Long studentId;
    static Long courseId;
    static Long enrollmentId;

    @AfterAll
    static void close() {
        JPAUtil.closeFactory();
    }

    @Test
    @Order(1)
    void testInsert() {
        Student s = new Student();
        s.setName("Tom");
        s.setSex("male");
        s.setAge(20);
        s.setPassword("tom123");
        s.setCreateTime(LocalDateTime.now());
        studentDao.save(s);
        studentId = s.getId();

        Course c = new Course();
        c.setName("Database");
        c.setTeacher("DrLee");
        courseDao.save(c);
        courseId = c.getId();

        Enrollment e = new Enrollment();
        e.setStudent(studentDao.findById(studentId).orElseThrow());
        e.setCourse(courseDao.findById(courseId).orElseThrow());
        e.setScore(88.5);
        e.setEnrollTime(LocalDateTime.now());
        enrollmentDao.save(e);
        enrollmentId = e.getId();

        Assertions.assertNotNull(studentId);
        Assertions.assertNotNull(courseId);
        Assertions.assertNotNull(enrollmentId);
    }

    @Test
    @Order(2)
    void testQuery() {
        Student s = studentDao.findById(studentId).orElse(null);
        Assertions.assertNotNull(s);
        System.out.println(s);

        List<Course> courses = courseDao.findAll();
        Assertions.assertFalse(courses.isEmpty());
        courses.forEach(System.out::println);
    }

    @Test
    @Order(3)
    void testUpdate() {
        Student s = studentDao.findById(studentId).orElseThrow();
        s.setAge(21);
        studentDao.update(s);

        Student after = studentDao.findById(studentId).orElseThrow();
        Assertions.assertEquals(21, after.getAge());
    }

    @Test
    @Order(4)
    void testDelete() {
        Enrollment e = enrollmentDao.findById(enrollmentId).orElseThrow();
        enrollmentDao.delete(e);
        Assertions.assertTrue(enrollmentDao.findById(enrollmentId).isEmpty());
    }

    @Test
    @Order(5)
    void testJPQLCustomQuery() {
        // 再插一条选课用于查询演示
        Enrollment e2 = new Enrollment();
        e2.setStudent(studentDao.findById(studentId).orElseThrow());
        e2.setCourse(courseDao.findById(courseId).orElseThrow());
        e2.setScore(95.0);
        e2.setEnrollTime(LocalDateTime.now());
        enrollmentDao.save(e2);

        List<Enrollment> res = enrollmentDao.findByTeacherAndMinScore("DrLee", 90.0);
        res.forEach(x -> System.out.println("EnrollmentId=" + x.getId() + ", score=" + x.getScore()));
        Assertions.assertFalse(res.isEmpty());
    }
}
