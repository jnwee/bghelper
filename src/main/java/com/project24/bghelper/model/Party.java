package com.project24.bghelper.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "parties")
public class Party {
  @Id
  private String id;
  private List<Companion> party;
  private boolean bg2;

  public List<Companion> getParty() {
    return party;
  }

  public void setParty(List<Companion> party) {
    this.party = party;
  }

  public boolean isBg2() {
    return bg2;
  }

  public void setBg2(boolean bg2) {
    this.bg2 = bg2;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
