package com.vnmntn.sinapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vnmntn.sinapi.model.Proof;
import com.vnmntn.sinapi.model.Sin;

public interface ProofRepository extends JpaRepository<Proof, Long> {

    List<Proof> findProofsByTagsId(Long tagId);
    List<Proof> findByTitleContaining(String title);
}
