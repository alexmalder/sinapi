package com.vnmntn.sinapi.init;

import com.vnmntn.sinapi.model.Role;
import com.vnmntn.sinapi.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import io.sentry.Sentry;


@Component
public class SeedRole {
    @Autowired
    RoleRepository roleRepository;

    void createRole(String name) {
        try {
            roleRepository.save(new Role(name));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        createRole("ROLE_USER");
        createRole("ROLE_MODERATOR");
        createRole("ROLE_ADMIN");
        System.out.println("Roles created");
    }
}