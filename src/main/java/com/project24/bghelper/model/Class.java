package com.project24.bghelper.model;

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
}
