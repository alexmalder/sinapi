package com.vnmntn.sinapi.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*; // for Spring Boot 3

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table
public class Tag {

    @Id
    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name = "proof_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "proof_id")
    )
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Proof> proofs;

    public Tag() {

    }
    public Tag(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Proof> getProofs() {
        return proofs;
    }

    public void setProofs(Set<Proof> proofs) {
        this.proofs = proofs;
    }
}
