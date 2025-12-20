package ynu.jackielinn.lab5.dto;

import lombok.Data;

@Data
public class StudentRegisterRequest {
    private String username;
    private String password;
    private String email;
}
