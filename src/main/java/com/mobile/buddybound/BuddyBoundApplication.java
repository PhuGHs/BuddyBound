package com.mobile.buddybound;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(name = "bearerAuth", scheme = "bearer", bearerFormat = "JWT", type= SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class BuddyBoundApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuddyBoundApplication.class, args);
    }

}
