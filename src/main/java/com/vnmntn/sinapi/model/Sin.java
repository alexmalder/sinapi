package com.vnmntn.sinapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table
public class Sin {

  @Id
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column
  private String title;

  @Column
  private String description;

  @Column
  private boolean published;

  public Sin() {

  }

  public Sin(String title, String description, boolean published) {
    this.title = title;
    this.description = description;
    this.published = published;
  }

  // getters and setters
  public long getId() {
    return id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setPublished(boolean published) {
    this.published = published;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public boolean isPublished() {
    return published;
  }

}
