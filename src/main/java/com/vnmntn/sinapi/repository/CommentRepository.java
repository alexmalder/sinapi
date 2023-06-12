package com.vnmntn.sinapi.repository;

import com.vnmntn.sinapi.model.Account;
import com.vnmntn.sinapi.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}