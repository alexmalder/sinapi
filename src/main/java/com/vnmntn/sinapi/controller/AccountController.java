package com.vnmntn.sinapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import io.sentry.Sentry;

import com.vnmntn.sinapi.exception.ResourceNotFoundException;
import com.vnmntn.sinapi.model.Account;
import com.vnmntn.sinapi.model.Sin;
import com.vnmntn.sinapi.repository.AccountRepository;
import com.vnmntn.sinapi.repository.SinRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts(@RequestParam(required = false) String username) {
        List<Account> accounts = new ArrayList<>();

        if (username == null)
            accounts.addAll(accountRepository.findAll());
        else
            accounts.addAll(accountRepository.findByUsernameContaining(username));
        if (accounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account _account = accountRepository.save(account);
        return new ResponseEntity<>(_account, HttpStatus.CREATED);
    }

    @PutMapping("/accounts")
    public ResponseEntity<Account> updateAccount(@RequestParam("id") UUID id, @RequestBody Account account) {
        Account _account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + id));

        _account.setEmail(account.getEmail());
        _account.setUsername(account.getUsername());
        _account.setPassword(account.getPassword());

        return new ResponseEntity<>(accountRepository.save(_account), HttpStatus.OK);
    }

    @DeleteMapping("/accounts")
    public ResponseEntity<HttpStatus> deleteAccount(@RequestParam("id") UUID id) {
        accountRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
