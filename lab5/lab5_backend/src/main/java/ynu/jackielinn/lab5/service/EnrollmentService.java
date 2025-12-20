package ynu.jackielinn.lab5.service;

import ynu.jackielinn.lab5.dao.*;
import ynu.jackielinn.lab5.dto.EnrollmentView;
import ynu.jackielinn.lab5.entity.*;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EnrollmentService {

    @Resource
    private EnrollmentDao enrollmentDao;

    @Resource
    private UserDao userDao;

    @Resource
    private CourseDao courseDao;

    public Enrollment enroll(Long studentId, Long courseId) {
        User s = userDao.findById(studentId).orElseThrow();
        if (s.getRole() != User.Role.STUDENT) {
            throw new IllegalArgumentException("studentId必须是学生账号");
        }

        if (enrollmentDao.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new IllegalArgumentException("该学生已选过该课程");
        }

        Course c = courseDao.findById(courseId).orElseThrow();

        Enrollment e = Enrollment.builder()
                .student(s)
                .course(c)
                .createTime(LocalDateTime.now())
                .build();

        return enrollmentDao.save(e);
    }

    public List<EnrollmentView> listByStudent(Long studentId) {
        return enrollmentDao.findByStudentId(studentId).stream().map(e ->
                EnrollmentView.builder()
                        .enrollmentId(e.getId())
                        .studentId(e.getStudent().getId())
                        .studentUsername(e.getStudent().getUsername())
                        .courseId(e.getCourse().getId())
                        .courseName(e.getCourse().getName())
                        .teacher(e.getCourse().getTeacher())
                        .credit(e.getCourse().getCredit())
                        .createTime(e.getCreateTime())
                        .build()
        ).toList();
    }

    public void dropByEnrollmentId(Long enrollmentId) {
        enrollmentDao.deleteById(enrollmentId);
    }

    public void dropByStudentCourse(Long studentId, Long courseId) {
        Enrollment e = enrollmentDao.findOne(studentId, courseId).orElseThrow();
        enrollmentDao.deleteById(e.getId());
    }
}
