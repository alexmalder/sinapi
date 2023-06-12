package com.vnmntn.sinapi.repository;

import com.vnmntn.sinapi.model.Account;
import com.vnmntn.sinapi.model.Proof;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    List<Account> findByUsernameContaining(String username);

}

