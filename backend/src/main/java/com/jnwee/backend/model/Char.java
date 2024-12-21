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
    private Progress progress;

    private LocalDateTime createdAt;
    private LocalDateTime diedAt;

    /**
     * Char is a Player Character
     * @param name Name of the character, can't be empty
     */
    public Char(String name) {
        this.name = name;
        status = Status.ALIVE;
        progress = Progress.BG1;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (this.status == Status.DEAD || this.status == Status.ASCENDED) {
            throw new IllegalArgumentException(
                this.name + " is " + this.status.name() + " which is final."
            );
        }
        if (status == Status.DEAD) {
            this.diedAt = LocalDateTime.now();
        }
        this.status = status;
    }

    public LocalDateTime getDiedAt() {
        return this.diedAt;
    }

    public void increaseProgress() {
        if (this.status == Status.DEAD || this.status == Status.ASCENDED) {
            throw new IllegalArgumentException(
                "Progress is fixed since Character is " + this.status.toString()
            );
        }
        switch (this.progress) {
            case Progress.BG1 -> this.progress = Progress.BG2;
            case Progress.BG2 -> this.progress = Progress.TOB;
            case Progress.TOB -> this.setStatus(Status.ASCENDED);
        }
    }

    public Progress getProgress() {
        return this.progress;
    }

    @Override
    public String toString() {
        return String.format(
            "Character nr %d with name %s and status: %s",
            this.id,
            this.name,
            this.status.toString()
        );
    }
}
