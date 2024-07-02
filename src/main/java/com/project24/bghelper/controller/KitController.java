package com.project24.bghelper.controller;

import com.project24.bghelper.model.Kit;
import com.project24.bghelper.model.KitType;
import com.project24.bghelper.service.CompanionService;
import com.project24.bghelper.service.KitService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kits")
public class KitController {

  private static final Logger logger = LoggerFactory.getLogger(KitController.class);
  private final CompanionService companionService;

  private KitService kitService;

  @Autowired
  public KitController(KitService kitService, CompanionService companionService) {
    this.kitService = kitService;
    this.companionService = companionService;
  }

  @GetMapping("")
  public List<Kit> getAllKits() {
    return kitService.getAllKits();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Kit> getKitById(@PathVariable String id) {
    return kitService.getKitById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound()
        .build());
  }

  @PostMapping("/create")
  public ResponseEntity<Kit> addKit(@RequestParam("name") String name,
                                    @RequestParam("isFighter") boolean isFighter,
                                    @RequestParam("isMage") boolean isMage,
                                    @RequestParam("isThief") boolean isThief) {
    Kit kit = new Kit();
    kit.setName(name);
    ArrayList<KitType> kitTypes = new ArrayList<>();
    if (isFighter) {
      kitTypes.add(KitType.FIGHTER);
    }
    if (isMage) {
      kitTypes.add(KitType.MAGE);
    }
    if (isThief) {
      kitTypes.add(KitType.THIEF);
    }
    kit.setKitTypes(kitTypes);
    Kit savedKit = kitService.addKit(kit);
    logger.info("Kit added with name: {} - and KitTypes: {}", savedKit.getName(),
        savedKit.getKitTypes());
    return ResponseEntity.status(201).body(savedKit);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteKit(@PathVariable String id) {
    kitService.deleteKit(id);
    return ResponseEntity.noContent().build();
  }
}
