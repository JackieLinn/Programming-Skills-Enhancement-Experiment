package ynu.jackielinn.lab5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ynu.jackielinn.lab5.dto.UpdateCourseRequest;
import ynu.jackielinn.lab5.entity.Course;
import ynu.jackielinn.lab5.service.CourseService;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "课程管理", description = "课程CRUD")
public class CourseController {

    @Resource
    private CourseService courseService;

    @PostMapping
    @Operation(summary = "新增课程")
    public Course add(@RequestBody Course c) {
        return courseService.add(c);
    }

    @GetMapping
    @Operation(summary = "课程列表")
    public List<Course> list() {
        return courseService.listAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新课程信息")
    public Course update(@PathVariable Long id, @RequestBody UpdateCourseRequest req) {
        return courseService.update(id, req.getName(), req.getTeacher(), req.getCredit());
    }

    @PutMapping("/{id}/teacher")
    @Operation(summary = "仅更新教师", description = "演示@Modifying更新")
    public int updateTeacher(@PathVariable Long id, @RequestParam String teacher) {
        return courseService.updateTeacher(id, teacher);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除课程")
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }
}
