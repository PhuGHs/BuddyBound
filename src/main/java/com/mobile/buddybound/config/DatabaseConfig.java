package com.mobile.buddybound.config;

import com.mobile.buddybound.model.entity.Role;
import com.mobile.buddybound.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DatabaseConfig {
    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner initDB() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                if (!roleRepository.existsByRoleName(Role.ADULTS)) {
                    Role adult = new Role();
                    adult.setRoleName(Role.ADULTS);
                    adult.setDescription("ADULTS");
                    roleRepository.save(adult);
                }
                if (!roleRepository.existsByRoleName(Role.CHILDREN)) {
                    Role child = new Role();
                    child.setRoleName(Role.CHILDREN);
                    child.setDescription("CHILDREN");
                    roleRepository.save(child);
                }
            }
        };
    }
}
