package ynu.jackielinn.lab5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ynu.jackielinn.lab5.dto.*;
import ynu.jackielinn.lab5.entity.User;
import ynu.jackielinn.lab5.service.AuthService;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证接口", description = "管理员登录 + 学生注册")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/admin/login")
    @Operation(summary = "管理员登录", description = "仅允许Role=ADMIN登录（用于测试后台）")
    public boolean adminLogin(@RequestBody LoginRequest req) {
        return authService.adminLogin(req.getUsername(), req.getPassword());
    }

    @PostMapping("/student/register")
    @Operation(summary = "学生注册", description = "只创建Role=STUDENT账号")
    public User studentRegister(@RequestBody StudentRegisterRequest req) {
        return authService.registerStudent(req.getUsername(), req.getPassword(), req.getEmail());
    }
}
