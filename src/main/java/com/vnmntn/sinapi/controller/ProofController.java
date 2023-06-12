package com.vnmntn.sinapi.controller;


import java.util.ArrayList;
import java.util.List;

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
import com.vnmntn.sinapi.model.Proof;
import com.vnmntn.sinapi.model.Sin;
import com.vnmntn.sinapi.repository.ProofRepository;
import com.vnmntn.sinapi.repository.SinRepository;
import com.vnmntn.sinapi.repository.AccountRepository;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ProofController {

    @Autowired
    ProofRepository proofRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SinRepository sinRepository;

    @GetMapping("/proofs")
    public ResponseEntity<List<Proof>> getAllProofs(@RequestParam(required = false) Long id,
                                                    @RequestParam(required = false) String title) {
        List<Proof> proofs = new ArrayList<>();

        if (id != null) {
            Proof proof = proofRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + id));
            proofs.add(proof);
        } else if (title != null) {
            proofs.addAll(proofRepository.findByTitleContaining(title));
        } else {
            proofs.addAll(proofRepository.findAll());
        }

        return new ResponseEntity<>(proofs, HttpStatus.OK);
    }

    @PostMapping("/proofs")
    public ResponseEntity<Proof> createProof(@RequestBody Proof proof, @RequestParam("accountId") long accountId, @RequestParam("sinId") long sinId) {
        Account _account = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Not found account with id = " + accountId));
        Sin _sin = sinRepository.findById(sinId).orElseThrow(() -> new ResourceNotFoundException("Not found sin with id = " + sinId));

        proof.setAccount(_account);
        proof.setSin(_sin);
        Proof _proof = proofRepository.save(proof);
        return new ResponseEntity<>(proof, HttpStatus.CREATED);
    }

    @PutMapping("/proofs")
    public ResponseEntity<Proof> updateProof(@RequestParam("id") long id, @RequestBody Proof proof) {
        Proof _proof = proofRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + id));

        _proof.setTitle(proof.getTitle());
        _proof.setLink(proof.getLink());

        return new ResponseEntity<>(proofRepository.save(_proof), HttpStatus.OK);
    }

    @DeleteMapping("/proofs")
    public ResponseEntity<HttpStatus> deleteProof(@RequestParam("id") long id) {
        proofRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
