package com.vnmntn.sinapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table
public class Comment {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String text;

    @Column
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proof_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Proof proof;

    public Comment() {

    }

    public Comment(String text) {
        this.text = text;
    }

    // getters and setters
    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
