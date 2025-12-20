package ynu.jackielinn.lab4.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.*;

import ynu.jackielinn.lab4.dto.ChangePasswordRequest;
import ynu.jackielinn.lab4.entity.User;
import ynu.jackielinn.lab4.service.UserService;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * 用户 REST API 控制器
 * @RestController = @Controller + @ResponseBody（自动返回 JSON）
 * @Tag 用于 Swagger 分组
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户相关CRUD与自定义操作")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping  // POST /api/users
    @Operation(summary = "注册用户", description = "新增用户")
    public User create(@RequestBody User user) {  // @RequestBody 解析 JSON 请求体
        return userService.register(user);
    }

    @GetMapping("/{id}")  // GET /api/users/{id}
    @Operation(summary = "查询用户", description = "按ID查询")
    public User get(@Parameter(description = "用户ID", required = true) @PathVariable Long id) {
        return userService.find(id).orElse(null);
    }

    @GetMapping("/search")  // GET /api/users/search?kw=xxx
    @Operation(summary = "搜索用户", description = "按关键字搜索username/email")
    public List<User> search(@RequestParam String kw) {
        return userService.search(kw);
    }

    @PutMapping("/{id}/password")  // PUT /api/users/{id}/password
    @Operation(summary = "修改密码", description = "通过@Modifying更新密码")
    public int changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest req) {
        return userService.changePassword(id, req.getNewPassword());
    }

    @DeleteMapping("/{id}")  // DELETE /api/users/{id}
    @Operation(summary = "删除用户", description = "按ID删除")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
