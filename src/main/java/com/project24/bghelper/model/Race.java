package com.project24.bghelper.model;

public enum Race {
  HUMAN,
  HALF_ELF,
  ELF,
  GNOME,
  HALFLING,
  DWARF,
  HALF_ORC;

  @Override
  public String toString() {
    String str = "";
    switch (this) {
      case HUMAN -> str = "Human";
      case HALF_ELF -> str = "Half-Elf";
      case ELF -> str = "Elf";
      case GNOME -> str = "Gnome";
      case HALFLING -> str = "Halfling";
      case DWARF -> str = "Dwarf";
      case HALF_ORC -> str = "Half-Orc";
    }
    return str;
  }
}
