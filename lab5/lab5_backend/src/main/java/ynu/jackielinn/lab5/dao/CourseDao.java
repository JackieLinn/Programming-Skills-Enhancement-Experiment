package ynu.jackielinn.lab5.dao;

import ynu.jackielinn.lab5.entity.Course;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CourseDao extends IBaseDao<Course, Long> {

    List<Course> findByTeacher(String teacher);

    List<Course> findByNameContaining(String keyword);

    Optional<Course> findByName(String name);

    @Query("select c from Course c where c.credit >= :minCredit order by c.credit desc")
    List<Course> findCreditAtLeast(@Param("minCredit") Integer minCredit);

    @Modifying
    @Transactional
    @Query("update Course c set c.teacher = :teacher where c.id = :id")
    int updateTeacher(@Param("id") Long id, @Param("teacher") String teacher);
}
