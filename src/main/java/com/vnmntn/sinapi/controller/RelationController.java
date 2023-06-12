package com.vnmntn.sinapi.controller;

import com.vnmntn.sinapi.exception.ResourceNotFoundException;
import com.vnmntn.sinapi.model.Proof;
import com.vnmntn.sinapi.model.Tag;
import com.vnmntn.sinapi.repository.ProofRepository;
import com.vnmntn.sinapi.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "https://api.vnmntn.com")
@RestController
@RequestMapping("/api/relations")
public class RelationController {
    @Autowired
    private ProofRepository proofRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/tags/proofs")
    public ResponseEntity<List<Tag>> getAllTagsByProofsId(@RequestParam(value = "proofId") Long proofId) {
        if (!proofRepository.existsById(proofId)) {
            throw new ResourceNotFoundException("Not found with id = " + proofId);
        }

        List<Tag> tags = tagRepository.findTagsByProofsId(proofId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/proofs/tags")
    public ResponseEntity<List<Proof>> getAllProofsByTagId(@RequestParam(value = "tagId") Long tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new ResourceNotFoundException("Not found Tag  with id = " + tagId);
        }

        List<Proof> proofs = proofRepository.findProofsByTagsId(tagId);
        return new ResponseEntity<>(proofs, HttpStatus.OK);
    }

    @PostMapping("/proofs/tags")
    public ResponseEntity<Tag> addTag(@RequestParam(value = "proofId") Long proofId, @RequestParam(value = "tagId") Long tagId) {
        Tag tag = proofRepository.findById(proofId).map(proof -> {
            Tag _tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + tagId));
            proof.addTag(_tag);
            proofRepository.save(proof);
            return _tag;
        }).orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + proofId));

        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }


    @DeleteMapping("/proofs/tags")
    public ResponseEntity<HttpStatus> deleteTagFromProof(@RequestParam(value = "proofId") Long proofId, @RequestParam(value = "tagId") Long tagId) {
        Proof proof = proofRepository.findById(proofId).orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + proofId));

        proof.removeTag(tagId);
        proofRepository.save(proof);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
