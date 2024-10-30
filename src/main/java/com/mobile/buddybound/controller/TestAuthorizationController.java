package com.mobile.buddybound.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/test-authorize")
@SecurityRequirement(name = "bearerAuth")
public class TestAuthorizationController {

    @GetMapping("/adult")
    @PreAuthorize("hasAuthority('ADULTS')")
    public String adultMethod() {
        return "Method is executed by adults";
    }

    @GetMapping("/child")
    @PreAuthorize("hasAuthority('CHILDREN')")
    public String childMethod() {
        return "Method is executed by children";
    }
}
