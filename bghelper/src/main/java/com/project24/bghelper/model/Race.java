package com.project24.bghelper.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Race {
  HUMAN("Human"),
  HALF_ELF("Half-Elf"),
  ELF("Elf"),
  GNOME("Gnome"),
  HALFLING("Halfling"),
  DWARF("Dwarf"),
  HALF_ORC("Half-Orc");

  private final String str;

  Race(String str) {
    this.str = str;
  }

  @Override
  public String toString() {
    return str;
  }

  @JsonCreator
  public static Race fromString(String key) {
    return key == null ? null : Race.valueOf(key.toUpperCase().replace("-", "_"));
  }
}
