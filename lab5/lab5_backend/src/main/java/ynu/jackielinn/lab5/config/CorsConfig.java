package ynu.jackielinn.lab5.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 跨域配置：允许前端 (Vue) 访问后端 API
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")              // 所有路径
                .allowedOriginPatterns("*")     // 允许所有来源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)         // 允许携带 Cookie
                .maxAge(3600);                  // 预检请求缓存1小时
    }
}
