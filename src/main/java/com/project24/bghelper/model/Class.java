package com.project24.bghelper.model;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "classes")
public class Class {
  @Id
  private String id;
  private HashMap<String, Integer> kitLevels;
  private Kit mainKit;
  private boolean isFighter;
  private boolean isThief;
  private boolean isMage;
  private boolean isMultiClass;
  private boolean isDualClass;

  //Map for toString Method :/
  private HashMap<String, String> kitNames = new HashMap<>();

  public HashMap<String, Integer> getKitLevels() {
    return kitLevels;
  }

  public void setKitLevels(HashMap<Kit, Integer> kitLevels) {
    if (this.kitLevels != null && kitNames != null) {
      this.kitLevels.clear();
      this.kitNames.clear();
    }
    isFighter = false;
    isMage = false;
    isThief = false;

    for (Kit kit : kitLevels.keySet()) {
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
      this.kitLevels.put(kit.getId(), kitLevels.get(kit));
      this.kitNames.put(kit.getId(), kit.getName());
    }
  }

  public Kit getMainKit() {
    return mainKit;
  }

  public void setMainKit(Kit mainKit) {
    this.mainKit = mainKit;
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

  public boolean isMage() {
    return isMage;
  }

  public boolean isThief() {
    return isThief;
  }

  public boolean isFighter() {
    return isFighter;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    if (!isDualClass && !isMultiClass) {
      return mainKit.toString();
    }
    StringBuilder str = new StringBuilder();
    if (isDualClass) {
      str.append("main-kit: ").append(mainKit.toString());
      for (String kit : kitLevels.keySet()) {
        if (kit != null && !kit.equals(mainKit.getId())) {
          str.append(" - ").append(kitNames.get(kit)).append(" lvl: ").append(kitLevels.get(kit));
        }
      }
    }
    if (isMultiClass) {
      for (String kit : kitLevels.keySet()) {
        if (kit != null) {
          str.append(kitNames.get(kit)).append(" / ");
        }
      }
    }
    return str.toString();
  }
}
