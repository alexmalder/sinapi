package com.vnmntn.sinapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vnmntn.sinapi.model.Proof;
import com.vnmntn.sinapi.model.Sin;

public interface ProofRepository extends JpaRepository<Proof, UUID> {

    List<Proof> findProofsByTagsId(UUID tagId);
    List<Proof> findByTitleContaining(String title);
}
