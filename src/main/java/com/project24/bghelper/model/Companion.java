package com.project24.bghelper.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "companions")
public class Companion {
  @Id
  private String id;
  private String name;
  private Integer strength;

  public Companion(String name,
                   Integer strength) {
    this.name = name;
    this.strength = strength;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getStrength() {
    return strength;
  }
}
