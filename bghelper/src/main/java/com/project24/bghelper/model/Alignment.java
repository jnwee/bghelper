package com.project24.bghelper.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Alignment {
  LAWFUL_GOOD("Lawful Good"),
  NEUTRAL_GOOD("Neutral Good"),
  CHAOTIC_GOOD("Chaotic Good"),
  LAWFUL_NEUTRAL("Lawful Neutral"),
  NEUTRAL("Neutral"),
  CHAOTIC_NEUTRAL("Chaotic Neutral"),
  LAWFUL_EVIL("Lawful Evil"),
  NEUTRAL_EVIL("Neutral Evil"),
  CHAOTIC_EVIL("Chaotic Evil");

  private final String str;

  Alignment(String str) {
    this.str = str;
  }

  @Override
  public String toString() {
    return str;
  }

  @JsonCreator
  public static Alignment fromString(String key) {
    return key == null ? null : Alignment.valueOf(key.toUpperCase().replace(" ", "_"));
  }
}
