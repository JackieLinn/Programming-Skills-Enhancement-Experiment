package ynu.jackielinn.lab5.service;

import ynu.jackielinn.lab5.dao.*;
import ynu.jackielinn.lab5.dto.EnrollmentView;
import ynu.jackielinn.lab5.entity.*;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 选课服务：选课、退课、查询选课记录
 */
@Service
@Transactional
public class EnrollmentService {

    @Resource
    private EnrollmentDao enrollmentDao;

    @Resource
    private UserDao userDao;

    @Resource
    private CourseDao courseDao;

    /**
     * 选课操作
     * 校验：1.必须是学生账号 2.不能重复选课
     */
    public Enrollment enroll(Long studentId, Long courseId) {
        User s = userDao.findById(studentId).orElseThrow();
        if (s.getRole() != User.Role.STUDENT) {
            throw new IllegalArgumentException("studentId必须是学生账号");
        }

        // 防止重复选课
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

    /**
     * 查询学生选课记录
     * 返回 DTO 视图，避免实体关联导致的懒加载/序列化问题
     */
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

    /** 按选课记录ID退课 */
    public void dropByEnrollmentId(Long enrollmentId) {
        enrollmentDao.deleteById(enrollmentId);
    }

    /** 按学生+课程退课 */
    public void dropByStudentCourse(Long studentId, Long courseId) {
        Enrollment e = enrollmentDao.findOne(studentId, courseId).orElseThrow();
        enrollmentDao.deleteById(e.getId());
    }
}
