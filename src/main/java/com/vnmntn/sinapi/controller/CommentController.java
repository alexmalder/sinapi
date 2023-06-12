package com.vnmntn.sinapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.vnmntn.sinapi.model.Comment;
import com.vnmntn.sinapi.model.Proof;
import com.vnmntn.sinapi.model.Sin;
import com.vnmntn.sinapi.repository.AccountRepository;
import com.vnmntn.sinapi.repository.CommentRepository;
import com.vnmntn.sinapi.repository.ProofRepository;
import com.vnmntn.sinapi.repository.SinRepository;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    ProofRepository proofRepository;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getAllSins(@RequestParam(required = false) UUID id, @RequestParam(required = false) UUID proofId) {
        List<Comment> comments = new ArrayList<>();

        if (id != null) {
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + id));
            comments.add(comment);
        } else if (proofId != null) {
            List<Comment> _comments = proofRepository.findCommentsById(proofId)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found with proof id = " + proofId));
            comments.addAll(_comments);
        } else {
            comments.addAll(commentRepository.findAll());
        }

        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/comments")
    public ResponseEntity<Comment> createSin(@RequestBody Comment comment) {
        Comment _comment = commentRepository.save(comment);
        return new ResponseEntity<>(_comment, HttpStatus.CREATED);
    }

    @PutMapping("/comments")
    public ResponseEntity<Comment> updateSin(@RequestParam("id") UUID id, @RequestBody Comment comment) {
        Comment _comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + id));

        _comment.setText(comment.getText());

        return new ResponseEntity<>(commentRepository.save(_comment), HttpStatus.OK);
    }

    @DeleteMapping("/comments")
    public ResponseEntity<HttpStatus> deleteSin(@RequestParam("id") UUID id) {
        commentRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}