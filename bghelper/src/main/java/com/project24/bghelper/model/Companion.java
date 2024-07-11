package com.project24.bghelper.model;

import com.project24.bghelper.exception.IllegalAbilityScoreException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "companions")
public class Companion extends Entity {
  @Id
  private String id;
  private boolean bg1;
  private boolean bg2;
  private boolean sod;

  /* TODO maybe
  private List<String> spritePictureIds;
   */

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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
