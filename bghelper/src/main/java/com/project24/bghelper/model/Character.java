package com.project24.bghelper.model;

import com.project24.bghelper.exception.IllegalAbilityScoreException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "characters")
public class Character extends Entity {
  @Id
  private String id;
  private boolean alive;
  private String deathReason;
  private String additionalText;
  private String partyBg1;
  private String partyBg2;

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
