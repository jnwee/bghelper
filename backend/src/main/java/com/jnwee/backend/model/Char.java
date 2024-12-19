package com.jnwee.backend.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "characters")
public class Char {

    @Id
    private String id;

    private String name;
    private String imageUrl;
    private Status status;

    private LocalDateTime createdAt;
    private LocalDateTime diedAt;

    /**
    Char is a Player Character
    @param name Name of the character, can't be empty
    */
    public Char(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can't be empty");
        }
        this.name = name;
        status = Status.ALIVE;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status == Status.DEAD || status == Status.ASCENDED) {
            throw new IllegalArgumentException(
                "Character is " + status.name() + " which is final."
            );
        }
        this.status = status;
    }

    public void setDiedAt(LocalDateTime time) {
        if (diedAt != null) {
            throw new IllegalArgumentException(
                "dietAt is already set and can't be set anew"
            );
        }
        this.diedAt = time;
    }

    public LocalDateTime getDiedAt() {
        return this.diedAt;
    }

    @Override
    public String toString() {
        return String.format(
            "Character nr %d with name %s and status dead: %s",
            this.id,
            this.name,
            this.status.toString()
        );
    }
}
