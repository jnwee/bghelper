package com.jnwee.backend.model;

import java.util.Locale;

public enum Status {
    ALIVE,
    DEAD,
    ASCENDED;

    @Override
    public String toString() {
        return this.name().toUpperCase(Locale.GERMAN);
    }
}
