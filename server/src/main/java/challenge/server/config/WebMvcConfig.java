package challenge.server.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 2023.1.27(금) 0h55 추가
//@Component
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD","GET","PUT","POST","OPTIONS")
                .allowedOrigins("https://66challenge.shop","http://66challenge.shop",
                        "https://66challenge-server.store","http://66challenge-server.store",
                        "http://localhost:3000", "http://13.209.179.193:8080", "http://localhost:8080",
                        "http://52.78.183.39:443","http://52.78.183.39:80")
                .exposedHeaders("Authorization", "Refresh", "Access-Control-Allow-Origin") // "*" = 모든 헤더값을 반환
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
