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

  @ManyToMany(mappedBy = "sins")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Set<Tag> tags;

  public Sin() {

  }

  public Sin(String title, String description, boolean published) {
    this.title = title;
    this.description = description;
    this.published = published;
  }

  // getters and setters

  public void addTag(Tag tag) {
    this.tags.add(tag);
    tag.getSins().add(this);
  }

  public void removeTag(long tagId) {
    Tag tag = this.tags.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
    if (tag != null) {
      this.tags.remove(tag);
      tag.getSins().remove(this);
    }
  }

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
