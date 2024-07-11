package com.project24.bghelper.model;

import com.project24.bghelper.exception.IllegalAbilityScoreException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "characters")
public class Character {
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
  private boolean isFighter;
  private boolean isThief;
  private boolean isFullMage;
  private boolean isHalfMage;
  private boolean isDruid;
  private boolean isFullCleric;
  private boolean isHalfCleric;
  private boolean alive;
  private String deathReason;
  private String additionalText;
  private String partyBg1;
  private String partyBg2;

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
      throw new IllegalAbilityScoreException(strength + " strength");
    }
    this.strength = strength;
  }

  public Integer getDexterity() {
    return dexterity;
  }

  public void setDexterity(Integer dexterity) {
    if (dexterity > 25 || dexterity < 1) {
      throw new IllegalAbilityScoreException(dexterity + " dexterity");
    }
    this.dexterity = dexterity;
  }

  public Integer getConstitution() {
    return constitution;
  }

  public void setConstitution(Integer constitution) {
    if (constitution > 25 || constitution < 1) {
      throw new IllegalAbilityScoreException(constitution + " constitution");
    }
    this.constitution = constitution;
  }

  public Integer getIntelligence() {
    return intelligence;
  }

  public void setIntelligence(Integer intelligence) {
    if (intelligence > 25 || intelligence < 1) {
      throw new IllegalAbilityScoreException(intelligence + " intelligence");
    }
    this.intelligence = intelligence;
  }

  public Integer getWisdom() {
    return wisdom;
  }

  public void setWisdom(Integer wisdom) {
    if (wisdom > 25 || wisdom < 1) {
      throw new IllegalAbilityScoreException(wisdom + " wisdom");
    }
    this.wisdom = wisdom;
  }

  public Integer getCharisma() {
    return charisma;
  }

  public void setCharisma(Integer charisma) {
    if (charisma > 25 || charisma < 1) {
      throw new IllegalAbilityScoreException(charisma + " charisma");
    }
    this.charisma = charisma;
  }

  public Integer getStatSum() {
    return strength + dexterity + constitution + intelligence + wisdom + charisma;
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

  public boolean isAlive() {
    return alive;
  }

  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  public String getDeathReason() {
    return deathReason;
  }

  public void setDeathReason(String deathReason) {
    this.deathReason = deathReason;
  }

  public String getAdditionalText() {
    return additionalText;
  }

  public void setAdditionalText(String additionalText) {
    this.additionalText = additionalText;
  }

  public String getPartyBg1() {
    return partyBg1;
  }

  public void setPartyBg1(String partyBg1) {
    this.partyBg1 = partyBg1;
  }

  public String getPartyBg2() {
    return partyBg2;
  }

  public void setPartyBg2(String partyBg2) {
    this.partyBg2 = partyBg2;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
