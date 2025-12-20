package ynu.jackielinn.lab5.dao;

import ynu.jackielinn.lab5.entity.Enrollment;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface EnrollmentDao extends IBaseDao<Enrollment, Long> {

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByCourseId(Long courseId);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("select e from Enrollment e where e.student.id = :sid and e.course.id = :cid")
    Optional<Enrollment> findOne(@Param("sid") Long studentId, @Param("cid") Long courseId);
}
