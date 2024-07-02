package com.project24.bghelper.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "companions")
public class Companion {
  @Id
  private String id;
  private String name;
  private String portraitId;
  private Alignment alignment;
  private Integer strength;
  private Integer dexterity;
  private Integer constitution;
  private Integer intelligence;
  private Integer wisdom;
  private Integer charisma;

  public String getPortraitId() {
    return portraitId;
  }

  public void setPortraitId(String portraitId) {
    this.portraitId = portraitId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getStrength() {
    return strength;
  }

  public void setStrength(Integer strength) {
    this.strength = strength;
  }

  public Integer getDexterity() {
    return dexterity;
  }

  public void setDexterity(Integer dexterity) {
    this.dexterity = dexterity;
  }

  public Integer getConstitution() {
    return constitution;
  }

  public void setConstitution(Integer constitution) {
    this.constitution = constitution;
  }

  public Integer getIntelligence() {
    return intelligence;
  }

  public void setIntelligence(Integer intelligence) {
    this.intelligence = intelligence;
  }

  public Integer getWisdom() {
    return wisdom;
  }

  public void setWisdom(Integer wisdom) {
    this.wisdom = wisdom;
  }

  public Integer getCharisma() {
    return charisma;
  }

  public void setCharisma(Integer charisma) {
    this.charisma = charisma;
  }

  public Alignment getAlignment() {
    return alignment;
  }

  public void setAlignment(Alignment alignment) {
    this.alignment = alignment;
  }
}
