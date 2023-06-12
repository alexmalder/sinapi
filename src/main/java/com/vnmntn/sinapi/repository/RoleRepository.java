package com.vnmntn.sinapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vnmntn.sinapi.model.ERole;
import com.vnmntn.sinapi.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(ERole name);
}