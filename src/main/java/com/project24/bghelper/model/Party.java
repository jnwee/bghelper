package com.project24.bghelper.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "parties")
public class Party {
  @Id
  private String id;
  private List<String> party = new ArrayList<>();
  private boolean bg2;

  public List<String> getParty() {
    return party;
  }

  public void addCompanion(String companionId) throws IllegalStateException {
    if (party.size() >= 5) {
      throw new IllegalStateException("Party is full, this should never occur");
    }
    this.party.add(companionId);
  }

  public void deleteCompanion(String companionId) {
    if (party.contains(companionId)) {
      party.remove(companionId);
    }
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
