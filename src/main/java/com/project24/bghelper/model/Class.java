package com.project24.bghelper.model;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "classes")
public class Class {
  @Id
  private String id;
  private HashMap<Kit, Integer> kitLevels;
  private boolean isFighter;
  private boolean isThief;
  private boolean isMage;
  private boolean isMultiClass;
  private boolean isDualClass;

  public HashMap<Kit, Integer> getKitLevels() {
    return kitLevels;
  }

  public void setKitLevels(HashMap<Kit, Integer> kitLevels) {
    this.kitLevels.clear();
    isFighter = false;
    isMage = false;
    isThief = false;
    this.kitLevels = kitLevels;
    for (Kit kit : this.kitLevels.keySet()) {
      ArrayList<KitType> kits = kit.getKitTypes();
      if (kits.contains(KitType.FIGHTER)) {
        isFighter = true;
      }
      if (kits.contains(KitType.MAGE)) {
        isMage = true;
      }
      if (kits.contains(KitType.THIEF)) {
        isThief = true;
      }
    }
  }

  public boolean isMultiClass() {
    return isMultiClass;
  }

  public void setMultiClass(boolean multiClass) {
    if (multiClass) {
      isDualClass = false;
    }
    isMultiClass = multiClass;
  }

  public boolean isDualClass() {
    return isDualClass;
  }

  public void setDualClass(boolean dualClass) {
    if (dualClass) {
      isMultiClass = false;
    }
    isDualClass = dualClass;
  }
}
