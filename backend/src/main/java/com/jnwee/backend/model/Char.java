package com.jnwee.backend.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "characters")
public class Char {

    @Id
    private String id;

    private String name;
    private boolean dead;
    private String imageUrl;

    private LocalDateTime createdAt;
    private LocalDateTime diedAt;

    /**
    Char is a Player Character
    @param name Name of the character, can't be empty
    @param dead Determines if the character is dead, true = dead
    */
    public Char(String name) {
        if (name == null || name.isBlank()) {
            name = "no name entered";
        }
        this.name = name;
        this.dead = false;
        this.createdAt = LocalDateTime.now();
        if (dead) {
            this.diedAt = LocalDateTime.now();
        }
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            name = "no name entered";
        }
        this.name = name;
    }

    public boolean isDead() {
        return this.dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
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

    public LocalDateTime getDiedAt() {
        return diedAt;
    }

    public void setDiedAt(LocalDateTime diedAt) {
        this.diedAt = diedAt;
    }

    @Override
    public String toString() {
        return String.format(
            "Character nr %d with name %s and status dead: %s",
            this.id,
            this.name,
            this.dead
        );
    }
}
