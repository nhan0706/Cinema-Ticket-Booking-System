package com.example.demo2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI cinemaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cinema Booking System API - Báo Cáo Tuần 1")
                        .description("RESTful API cho Hệ thống Đặt Vé Xem Phim Trực Tuyến - Module Master Data & Quản lý Phim, Rạp, Banner, Ngày lễ.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Nguyễn Thành Nhân")
                                .email("nhan@cinema.com"))
                        .license(new License().name("Apache 2.0")));
    }
}
