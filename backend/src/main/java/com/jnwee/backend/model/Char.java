package com.jnwee.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "characters")
public class Char {

    @Id
    private String id;

    private String name;
    private boolean dead;

    /**
    Char is a Player Character
    @param name Name of the character, can't be empty
    @param dead Determines if the character is dead, true = dead
    */
    public Char(String name, boolean dead) {
        if (name == null || name.isBlank()) {
            name = "no name entered";
        }
        this.name = name;
        this.dead = dead;
        this.toString();
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
