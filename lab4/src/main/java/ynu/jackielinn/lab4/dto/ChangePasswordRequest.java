package ynu.jackielinn.lab4.dto;

import lombok.Data;

/**
 * 修改密码请求 DTO（Data Transfer Object）
 */
@Data
public class ChangePasswordRequest {
    private String newPassword;
}
