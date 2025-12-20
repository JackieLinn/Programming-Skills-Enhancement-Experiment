package ynu.jackielinn.lab5.service;

import ynu.jackielinn.lab5.dao.CourseDao;
import ynu.jackielinn.lab5.entity.Course;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseService {

    @Resource
    private CourseDao courseDao;

    public Course add(Course c) {
        return courseDao.save(c);
    }

    public List<Course> listAll() {
        return courseDao.findAll();
    }

    public Course update(Long id, String name, String teacher, Integer credit) {
        Course c = courseDao.findById(id).orElseThrow();
        if (name != null && !name.isBlank()) c.setName(name);
        if (teacher != null && !teacher.isBlank()) c.setTeacher(teacher);
        if (credit != null) c.setCredit(credit);
        return courseDao.save(c);
    }

    public int updateTeacher(Long id, String teacher) {
        return courseDao.updateTeacher(id, teacher);
    }

    public void delete(Long id) {
        courseDao.deleteById(id);
    }
}
