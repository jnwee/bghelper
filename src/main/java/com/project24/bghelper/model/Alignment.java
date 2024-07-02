package com.project24.bghelper.model;

public enum Alignment {
  LAWFUL_GOOD,
  NEUTRAL_GOOD,
  CHAOTIC_GOOD,
  LAWFUL_NEUTRAL,
  NEUTRAL,
  CHAOTIC_NEUTRAL,
  LAWFUL_EVIL,
  NEUTRAL_EVIL,
  CHAOTIC_EVIL;

  @Override
  public String toString() {
    String str = "";
    switch(this) {
      case LAWFUL_GOOD -> str = "Lawful Good";
      case NEUTRAL_GOOD -> str = "Neutral Good";
      case CHAOTIC_GOOD -> str = "Chaotic Good";
      case LAWFUL_NEUTRAL -> str = "Lawful Neutral";
      case NEUTRAL -> str = "Neutral";
      case CHAOTIC_NEUTRAL -> str = "Chaotic Neutral";
      case LAWFUL_EVIL -> str = "Lawful Evil";
      case NEUTRAL_EVIL -> str = "Neutral Evil";
      case CHAOTIC_EVIL -> str = "Chaotic Evil";
      default -> str = "unknown";
    }
    return str;
  }
}
