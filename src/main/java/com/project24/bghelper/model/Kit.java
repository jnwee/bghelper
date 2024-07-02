package com.project24.bghelper.model;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "kits")
public class Kit {
  @Id
  private String id;
  private String name;
  private ArrayList<KitType> kitTypes;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArrayList<KitType> getKitTypes() {
    return kitTypes;
  }

  public void setKitTypes(ArrayList<KitType> kitTypes) {
    this.kitTypes = kitTypes;
  }
}