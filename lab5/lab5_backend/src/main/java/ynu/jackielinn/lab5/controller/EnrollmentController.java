package ynu.jackielinn.lab5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ynu.jackielinn.lab5.dto.EnrollmentRequest;
import ynu.jackielinn.lab5.dto.EnrollmentView;
import ynu.jackielinn.lab5.entity.Enrollment;
import ynu.jackielinn.lab5.service.EnrollmentService;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@Tag(name = "选课管理", description = "管理员维护选课关系")
public class EnrollmentController {

    @Resource
    private EnrollmentService enrollmentService;

    @PostMapping
    @Operation(summary = "选课", description = "为学生添加一门课程")
    public Enrollment enroll(@RequestBody EnrollmentRequest req) {
        return enrollmentService.enroll(req.getStudentId(), req.getCourseId());
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "查看学生选课", description = "返回EnrollmentView，避免懒加载序列化问题")
    public List<EnrollmentView> listByStudent(@PathVariable Long studentId) {
        return enrollmentService.listByStudent(studentId);
    }

    @DeleteMapping("/{enrollmentId}")
    @Operation(summary = "按选课记录退课", description = "通过enrollmentId删除")
    public void drop(@PathVariable Long enrollmentId) {
        enrollmentService.dropByEnrollmentId(enrollmentId);
    }

    @DeleteMapping
    @Operation(summary = "按学生+课程退课", description = "通过studentId+courseId删除")
    public void dropByStudentCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        enrollmentService.dropByStudentCourse(studentId, courseId);
    }
}
