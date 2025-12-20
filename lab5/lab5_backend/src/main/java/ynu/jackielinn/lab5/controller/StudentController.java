package ynu.jackielinn.lab5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import ynu.jackielinn.lab5.dto.UpdateEmailRequest;
import ynu.jackielinn.lab5.entity.User;
import ynu.jackielinn.lab5.service.UserService;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Tag(name = "学生管理", description = "管理员维护学生信息（User表中Role=STUDENT）")
public class StudentController {

    @Resource
    private UserService userService;

    @GetMapping
    @Operation(summary = "学生列表", description = "查询所有学生账号")
    public List<User> list() {
        return userService.listStudents();
    }

    @GetMapping("/search")
    @Operation(summary = "搜索学生", description = "按关键字搜索学生(username/email)")
    public List<User> search(@RequestParam String kw) {
        return userService.searchStudents(kw);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新学生信息", description = "更新学生 username/password/email（不传则不改）")
    public User update(
            @Parameter(description = "学生ID", required = true) @PathVariable Long id,
            @RequestBody User body
    ) {
        return userService.updateStudent(id, body.getUsername(), body.getPassword(), body.getEmail());
    }

    @PutMapping("/{id}/email")
    @Operation(summary = "仅更新邮箱", description = "演示@Modifying更新")
    public int updateEmail(@PathVariable Long id, @RequestBody UpdateEmailRequest req) {
        return userService.updateEmail(id, req.getEmail());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除学生", description = "按ID删除账号")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
