package com.project24.bghelper.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "companions")
public class Companion {
  @Id
  private String id;
  private String name;
  private Integer strength;
  private String portraitId;

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
}
