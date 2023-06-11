package com.vnmntn.sinapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import io.sentry.Sentry;

import com.vnmntn.sinapi.exception.ResourceNotFoundException;
import com.vnmntn.sinapi.model.Sin;
import com.vnmntn.sinapi.repository.SinRepository;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class SinController {

	@Autowired
	SinRepository sinRepository;

	@GetMapping("/sins")
	public ResponseEntity<List<Sin>> getAllSins(@RequestParam(required = false) String title) {
		List<Sin> sins = new ArrayList<Sin>();

		if (title == null)
			sins.addAll(sinRepository.findAll());
		else
			sins.addAll(sinRepository.findByTitleContaining(title));

		if (sins.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(sins, HttpStatus.OK);
	}

	@GetMapping("/sins/{id}")
	public ResponseEntity<Sin> getSinById(@PathVariable("id") long id) {
		Sin sin = sinRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + id));

		return new ResponseEntity<>(sin, HttpStatus.OK);
	}

	@PostMapping("/sins")
	public ResponseEntity<Sin> createSin(@RequestBody Sin sin) {
		Sin _sin = sinRepository.save(new Sin(sin.getTitle(), sin.getDescription(), true));
		return new ResponseEntity<>(_sin, HttpStatus.CREATED);
	}

	@PutMapping("/sins/{id}")
	public ResponseEntity<Sin> updateSin(@PathVariable("id") long id, @RequestBody Sin sin) {
		Sin _sin = sinRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + id));

		_sin.setTitle(sin.getTitle());
		_sin.setDescription(sin.getDescription());
		_sin.setPublished(sin.isPublished());

		return new ResponseEntity<>(sinRepository.save(_sin), HttpStatus.OK);
	}

	@DeleteMapping("/sins/{id}")
	public ResponseEntity<HttpStatus> deleteSin(@PathVariable("id") long id) {
		sinRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/sins/published")
	public ResponseEntity<List<Sin>> findByPublished() {
		List<Sin> sins = sinRepository.findByPublished(true);

		if (sins.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(sins, HttpStatus.OK);
	}
}