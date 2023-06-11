package com.vnmntn.sinapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vnmntn.sinapi.model.Sin;
import com.vnmntn.sinapi.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByNameContaining(String name);

    List<Tag> findTagsByProofsId(Long proofId);
}