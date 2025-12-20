package ynu.jackielinn.lab4.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Lab4 API")
                        .version("1.0")
                        .description("SpringBoot + JPA + Knife4j 实验接口文档"));
    }
}
