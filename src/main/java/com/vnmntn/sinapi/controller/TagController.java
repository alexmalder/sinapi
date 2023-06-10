package com.vnmntn.sinapi.controller;

import com.vnmntn.sinapi.exception.ResourceNotFoundException;
import com.vnmntn.sinapi.model.Tag;
import com.vnmntn.sinapi.model.Sin;
import com.vnmntn.sinapi.repository.TagRepository;
import com.vnmntn.sinapi.repository.SinRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TagController {

    @Autowired
    private SinRepository sinRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags() {

        List<Tag> tags = new ArrayList<Tag>(tagRepository.findAll());

        if (tags.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/tags/sins")
    public ResponseEntity<List<Tag>> getAllTagsBySinId(@RequestParam(value = "sinId") Long sinId) {
        if (!sinRepository.existsById(sinId)) {
            throw new ResourceNotFoundException("Not found with id = " + sinId);
        }

        List<Tag> tags = tagRepository.findTagsBySinsId(sinId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/tag")
    public ResponseEntity<Tag> getTagsById(@RequestParam(value = "id") Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + id));

        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @GetMapping("/sins/tags")
    public ResponseEntity<List<Sin>> getAllSinsByTagId(@RequestParam(value = "tagId") Long tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new ResourceNotFoundException("Not found Tag  with id = " + tagId);
        }

        List<Sin> sins = sinRepository.findSinsByTagsId(tagId);
        return new ResponseEntity<>(sins, HttpStatus.OK);
    }

    @PostMapping("/tags")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag _tag = tagRepository.save(new Tag(tag.getName()));
        return new ResponseEntity<>(_tag, HttpStatus.CREATED);
    }

    @PostMapping("/sins/tags")
    public ResponseEntity<Tag> addTag(@RequestParam(value = "sinId") Long sinId, @RequestParam(value = "tagId") Long tagId) {
        Tag tag = sinRepository.findById(sinId).map(sin -> {
            Tag _tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + tagId));
            sin.addTag(_tag);
            sinRepository.save(sin);
            return _tag;
        }).orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + sinId));

        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @PutMapping("/tags")
    public ResponseEntity<Tag> updateTag(@RequestParam("id") long id, @RequestBody Tag tagRequest) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TagId " + id + "not found"));

        tag.setName(tagRequest.getName());

        return new ResponseEntity<>(tagRepository.save(tag), HttpStatus.OK);
    }

    @DeleteMapping("/sins/tags")
    public ResponseEntity<HttpStatus> deleteTagFromSin(@RequestParam(value = "sinId") Long sinId, @RequestParam(value = "tagId") Long tagId) {
        Sin sin = sinRepository.findById(sinId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + sinId));

        sin.removeTag(tagId);
        sinRepository.save(sin);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tags")
    public ResponseEntity<HttpStatus> deleteTag(@RequestParam("id") long id) {
        tagRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
