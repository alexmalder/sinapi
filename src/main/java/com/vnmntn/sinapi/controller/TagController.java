package com.vnmntn.sinapi.controller;

import com.vnmntn.sinapi.exception.ResourceNotFoundException;
import com.vnmntn.sinapi.model.Proof;
import com.vnmntn.sinapi.model.Tag;
import com.vnmntn.sinapi.model.Sin;
import com.vnmntn.sinapi.repository.ProofRepository;
import com.vnmntn.sinapi.repository.TagRepository;
import com.vnmntn.sinapi.repository.SinRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TagController {

    @Autowired
    private ProofRepository proofRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags(@RequestParam(required = false) UUID id, @RequestParam(required = false) String name) {

        List<Tag> tags = new ArrayList<>();

        if (id != null) {
            Tag tag = tagRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + id));
            tags.add(tag);
        } else if (name != null) {
            tags.addAll(tagRepository.findByNameContaining(name));
        } else {
            tags.addAll(tagRepository.findAll());
        }

        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping("/tags")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag _tag = tagRepository.save(new Tag(tag.getName()));
        return new ResponseEntity<>(_tag, HttpStatus.CREATED);
    }

    @PutMapping("/tags")
    public ResponseEntity<Tag> updateTag(@RequestParam("id") UUID id, @RequestBody Tag tagRequest) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TagId " + id + "not found"));

        tag.setName(tagRequest.getName());

        return new ResponseEntity<>(tagRepository.save(tag), HttpStatus.OK);
    }

    @DeleteMapping("/tags")
    public ResponseEntity<HttpStatus> deleteTag(@RequestParam("id") UUID id) {
        tagRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // add tag for existed proof[correct]
    @PostMapping("/tags/proofs")
    public ResponseEntity<Tag> addTag(@RequestParam(value = "proofId") UUID proofId, @RequestParam(value = "tagId") UUID tagId) {
        Tag tag = proofRepository.findById(proofId).map(proof -> {
            Tag _tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + tagId));
            proof.addTag(_tag);
            proofRepository.save(proof);
            return _tag;
        }).orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + proofId));

        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    // delete tag from existed proof[correct]
    @DeleteMapping("/tags/proofs")
    public ResponseEntity<HttpStatus> deleteTagFromProof(@RequestParam(value = "proofId") UUID proofId, @RequestParam(value = "tagId") UUID tagId) {
        Proof proof = proofRepository.findById(proofId).orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + proofId));

        proof.removeTag(tagId);
        proofRepository.save(proof);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // get all tags by proof id
    @GetMapping("/tags/proofs")
    public ResponseEntity<List<Tag>> getAllTagsByProofId(@RequestParam(value = "proofId") UUID proofId) {
        if (!proofRepository.existsById(proofId)) {
            throw new ResourceNotFoundException("Not found with id = " + proofId);
        }

        List<Tag> tags = tagRepository.findTagsByProofsId(proofId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
}
