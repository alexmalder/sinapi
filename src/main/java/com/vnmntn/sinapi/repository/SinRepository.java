package com.vnmntn.sinapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vnmntn.sinapi.model.Sin;

public interface SinRepository extends JpaRepository<Sin, UUID> {
  List<Sin> findByPublished(boolean published);

  List<Sin> findByTitleContaining(String title);

}
