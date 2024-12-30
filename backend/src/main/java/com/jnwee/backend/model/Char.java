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
    private Race race;
    private CharacterClass characterClass;
    private Alignment alignment;
    private CompanionBg1[] partyBg1;
    private CompanionBg2[] partyBg2;
    private String deathNote;

    private LocalDateTime createdAt;
    private LocalDateTime diedAt;

    /**
     * Char is a Player Character
     * @param name Name of the character, can't be empty
     */
    public Char(
        String name,
        Race race,
        CharacterClass characterClass,
        Alignment alignment
    ) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name can't be empty");
        }
        // if (race == null || characterClass == null || alignment == null) {
        //     throw new IllegalArgumentException("Character must have a race, class and alignment");
        // }
        this.name = name;
        this.race = race;
        this.characterClass = characterClass;
        this.alignment = alignment;
        status = Status.ALIVE;
        progress = Progress.BG1;
        this.partyBg1 = new CompanionBg1[5];
        this.partyBg2 = new CompanionBg2[5];
        this.createdAt = LocalDateTime.now();
        this.deathNote = "";
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Race getRace() {
        return this.race;
    }

    public CharacterClass getCharacterClass() {
        return this.characterClass;
    }

    public Alignment getAlignment() {
        return this.alignment;
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

    public void setCompanionBg1(CompanionBg1 companion, Integer index) {
        if (index > 4 || index < 0) {
            throw new IllegalArgumentException(
                "Companion index can't be larger than 4"
            );
        }
        for (CompanionBg1 party : partyBg1) {
            if (companion == party) {
                throw new IllegalArgumentException(
                    "Companions can only be in the party once"
                );
            }
        }
        partyBg1[index] = companion;
    }

    public CompanionBg1[] getPartyBg1() {
        return this.partyBg1;
    }

    public void setCompanionBg2(CompanionBg2 companion, Integer index) {
        if (index > 4 || index < 0) {
            throw new IllegalArgumentException(
                "Companion index can't be larger than 4"
            );
        }
        for (CompanionBg2 party : partyBg2) {
            if (companion == party) {
                throw new IllegalArgumentException(
                    "Companions can only be in the party once"
                );
            }
        }
        partyBg2[index] = companion;
    }

    public CompanionBg2[] getPartyBg2() {
        return this.partyBg2;
    }

    public void setDeathNote(String text) {
        if (this.status != Status.DEAD) {
            throw new IllegalArgumentException(
                "Can't add death note to character who's not dead"
            );
        }
        this.deathNote = text;
    }

    public String getDeathNote() {
        if (this.deathNote == null) {
            this.deathNote = "";
        }
        return this.deathNote;
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
