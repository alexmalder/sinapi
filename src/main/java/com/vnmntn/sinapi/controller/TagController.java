package com.vnmntn.sinapi.controller;

import com.vnmntn.sinapi.exception.ResourceNotFoundException;
import com.vnmntn.sinapi.model.Tag;
import com.vnmntn.sinapi.model.Tutorial;
import com.vnmntn.sinapi.repository.TagRepository;
import com.vnmntn.sinapi.repository.TutorialRepository;

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
    private TutorialRepository tutorialRepository;

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

    @GetMapping("/tags/tutorials")
    public ResponseEntity<List<Tag>> getAllTagsByTutorialId(@RequestParam(value = "tutorialId") Long tutorialId) {
        if (!tutorialRepository.existsById(tutorialId)) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
        }

        List<Tag> tags = tagRepository.findTagsByTutorialsId(tutorialId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/tag")
    public ResponseEntity<Tag> getTagsById(@RequestParam(value = "id") Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + id));

        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @GetMapping("/tutorials/tags")
    public ResponseEntity<List<Tutorial>> getAllTutorialsByTagId(@RequestParam(value = "tagId") Long tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new ResourceNotFoundException("Not found Tag  with id = " + tagId);
        }

        List<Tutorial> tutorials = tutorialRepository.findTutorialsByTagsId(tagId);
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @PostMapping("/tutorials/tags")
    public ResponseEntity<Tag> addTag(@RequestParam(value = "tutorialId") Long tutorialId, @RequestBody Tag tagRequest) {
        Tag tag = tutorialRepository.findById(tutorialId).map(tutorial -> {
            long tagId = tagRequest.getId();

            // tag is existed
            if (tagId != 0L) {
                Tag _tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + tagId));
                tutorial.addTag(_tag);
                tutorialRepository.save(tutorial);
                return _tag;
            }

            // add and create new Tag
            tutorial.addTag(tagRequest);
            return tagRepository.save(tagRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));

        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @PutMapping("/tags")
    public ResponseEntity<Tag> updateTag(@RequestParam("id") long id, @RequestBody Tag tagRequest) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TagId " + id + "not found"));

        tag.setName(tagRequest.getName());

        return new ResponseEntity<>(tagRepository.save(tag), HttpStatus.OK);
    }

    @DeleteMapping("/tutorials/tags")
    public ResponseEntity<HttpStatus> deleteTagFromTutorial(@RequestParam(value = "tutorialId") Long tutorialId, @RequestParam(value = "tagId") Long tagId) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));

        tutorial.removeTag(tagId);
        tutorialRepository.save(tutorial);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tags")
    public ResponseEntity<HttpStatus> deleteTag(@RequestParam("id") long id) {
        tagRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
