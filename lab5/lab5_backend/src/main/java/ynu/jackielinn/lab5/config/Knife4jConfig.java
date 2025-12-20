package ynu.jackielinn.lab5.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Lab5 教务管理系统 API")
                .version("1.0")
                .description("统一用户表(管理员维护学生/课程/选课"));
    }
}
