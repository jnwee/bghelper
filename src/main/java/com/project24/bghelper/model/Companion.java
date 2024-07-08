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
  private Race race;
  private String charClass;
  private Integer strength;
  private Integer dexterity;
  private Integer constitution;
  private Integer intelligence;
  private Integer wisdom;
  private Integer charisma;
  private boolean bg1;
  private boolean bg2;
  private boolean sod;
  private boolean isFighter;
  private boolean isThief;
  private boolean isFullMage;
  private boolean isHalfMage;
  private boolean isDruid;
  private boolean isFullCleric;
  private boolean isHalfCleric;

  /* TODO maybe
  private List<String> spritePictureIds;
   */

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
    if (strength > 25 || strength < 1) {
      throw new IllegalArgumentException("strength stat not valid");
    }
    this.strength = strength;
  }

  public Integer getDexterity() {
    return dexterity;
  }

  public void setDexterity(Integer dexterity) {
    if (dexterity > 25 || dexterity < 1) {
      throw new IllegalArgumentException("dexterity stat not valid");
    }
    this.dexterity = dexterity;
  }

  public Integer getConstitution() {
    return constitution;
  }

  public void setConstitution(Integer constitution) {
    if (constitution > 25 || constitution < 1) {
      throw new IllegalArgumentException("constitution stat not valid");
    }
    this.constitution = constitution;
  }

  public Integer getIntelligence() {
    return intelligence;
  }

  public void setIntelligence(Integer intelligence) {
    if (intelligence > 25 || intelligence < 1) {
      throw new IllegalArgumentException("intelligence stat not valid");
    }
    this.intelligence = intelligence;
  }

  public Integer getWisdom() {
    return wisdom;
  }

  public void setWisdom(Integer wisdom) {
    if (wisdom > 25 || wisdom < 1) {
      throw new IllegalArgumentException("wisdom stat not valid");
    }
    this.wisdom = wisdom;
  }

  public Integer getCharisma() {
    return charisma;
  }

  public void setCharisma(Integer charisma) {
    if (charisma > 25 || charisma < 1) {
      throw new IllegalArgumentException("charisma stat not valid");
    }
    this.charisma = charisma;
  }

  public Alignment getAlignment() {
    return alignment;
  }

  public void setAlignment(Alignment alignment) {
    this.alignment = alignment;
  }

  public Race getRace() {
    return race;
  }

  public void setRace(Race race) {
    this.race = race;
  }

  public String getCharClass() {
    return charClass;
  }

  public void setCharClass(String charClass) {
    this.charClass = charClass;
  }

  public boolean isBg1() {
    return bg1;
  }

  public void setBg1(boolean bg1) {
    this.bg1 = bg1;
  }

  public boolean isBg2() {
    return bg2;
  }

  public void setBg2(boolean bg2) {
    this.bg2 = bg2;
  }

  public boolean isSod() {
    return sod;
  }

  public void setSod(boolean sod) {
    this.sod = sod;
  }

  public boolean isFighter() {
    return isFighter;
  }

  public void setFighter(boolean fighter) {
    isFighter = fighter;
  }

  public boolean isThief() {
    return isThief;
  }

  public void setThief(boolean thief) {
    isThief = thief;
  }

  public boolean isFullMage() {
    return isFullMage;
  }

  public void setFullMage(boolean fullMage) {
    isFullMage = fullMage;
  }

  public boolean isHalfMage() {
    return isHalfMage;
  }

  public void setHalfMage(boolean halfMage) {
    isHalfMage = halfMage;
  }

  public boolean isFullCleric() {
    return isFullCleric;
  }

  public void setFullCleric(boolean fullCleric) {
    isFullCleric = fullCleric;
  }

  public boolean isDruid() {
    return isDruid;
  }

  public void setDruid(boolean druid) {
    isDruid = druid;
  }

  public boolean isHalfCleric() {
    return isHalfCleric;
  }

  public void setHalfCleric(boolean halfCleric) {
    isHalfCleric = halfCleric;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
