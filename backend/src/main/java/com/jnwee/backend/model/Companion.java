package com.jnwee.backend.model;

public enum Companion {
    AJANTIS("BG1"),
    ALORA("BG1"),
    BAELOTH("BG1"),
    BRANWEN("BG1"),
    CORAN("BG1"),
    DORN("BG1"),
    DYNAHEIR("BG1"),
    EDWIN("BG1"),
    ELDOTH("BG1"),
    FALDORN("BG1"),
    GARRICK("BG1"),
    IMOEN("BG1"),
    JAHEIRA("BG1"),
    KAGAIN("BG1"),
    KHALID("BG1"),
    KIVAN("BG1"),
    MINSC("BG1"),
    MONTARON("BG1"),
    NEERA("BG1"),
    QUAYLE("BG1"),
    RASAAD("BG1"),
    SAFANA("BG1"),
    SHARTEEL("BG1"),
    SKIE("BG1"),
    TIAX("BG1"),
    VICONIA("BG1"),
    XAN("BG1"),
    XZAR("BG1"),
    YESLICK("BG1");

    private final String game;

    // Constructor
    Companion(String game) {
        this.game = game;
    }

    // Getter
    public String getGame() {
        return game;
    }

    // BG1 Filter
    public static List<String> getBG1Companions() {
        return Arrays.stream(values())
            .filter(companion -> companion.getGame().equals("BG1"))
            .map(Enum::name)
            .toList();
    }

    // BG2 Filter
    public static List<String> getBG2Companions() {
        return Arrays.stream(values())
            .filter(companion -> companion.getGame().equals("BG2"))
            .map(Enum::name)
            .toList();
    }
}
