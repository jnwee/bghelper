package com.project24.bghelper.model;

import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "companions")
public class Companion {
  @Id
  private String id;
  private String name;
  //private Map<String, Integer> stats;
}
