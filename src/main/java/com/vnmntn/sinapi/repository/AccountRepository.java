package com.vnmntn.sinapi.repository;

import com.vnmntn.sinapi.model.Account;
import com.vnmntn.sinapi.model.Proof;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUsernameContaining(String username);

}

