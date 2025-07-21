package com.damon;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author damon du/minghongdud
 */
@EnableAdminServer
@SpringBootApplication
public class LibAdminServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibAdminServerApplication.class);
    }
}
