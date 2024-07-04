package com.project24.bghelper.controller;

import com.project24.bghelper.model.Alignment;
import com.project24.bghelper.model.Class;
import com.project24.bghelper.model.Companion;
import com.project24.bghelper.model.Kit;
import com.project24.bghelper.service.ClassService;
import com.project24.bghelper.service.CompanionService;
import com.project24.bghelper.service.FileService;
import com.project24.bghelper.service.KitService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/companions")
public class CompanionController {

  private static final Logger logger = LoggerFactory.getLogger(CompanionController.class);

  CompanionService companionService;
  FileService fileService;
  ClassService classService;
  KitService kitService;

  @Autowired
  public CompanionController(CompanionService companionService, FileService fileService,
                             ClassService classService, KitService kitService) {
    this.companionService = companionService;
    this.fileService = fileService;
    this.classService = classService;
    this.kitService = kitService;
  }

  @GetMapping("")
  public List<Companion> getAllCompanions() {
    return companionService.getAllCompanions();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Companion> getCompanionById(@PathVariable String id) {
    return companionService.getCompanionById(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/create")
  public ResponseEntity<Companion> addCompanion(@RequestParam("name") String name,
                                                @RequestParam("portrait") MultipartFile image,
                                                @RequestParam("alignment") Alignment alignment,
                                                @RequestParam("str") Integer str,
                                                @RequestParam("dex") Integer dex,
                                                @RequestParam("con") Integer con,
                                                @RequestParam("int") Integer inT,
                                                @RequestParam("wis") Integer wis,
                                                @RequestParam("cha") Integer cha,
                                                @RequestParam(value = "kit1", defaultValue = "null")
                                                String kitId1,
                                                @RequestParam(value = "kit1lvl", defaultValue = "0")
                                                Integer kit1lvl,
                                                @RequestParam(value = "kit2", defaultValue = "null")
                                                String kitId2,
                                                @RequestParam(value = "kit2lvl", defaultValue = "0")
                                                Integer kit2lvl,
                                                @RequestParam(value = "kit3", defaultValue = "null")
                                                String kitId3,
                                                @RequestParam(value = "kit3lvl", defaultValue = "0")
                                                Integer kit3lvl,
                                                @RequestParam(value = "bg1", defaultValue = "false")
                                                Boolean bg1,
                                                @RequestParam(value = "sod", defaultValue = "false")
                                                Boolean sod,
                                                @RequestParam(value = "bg2", defaultValue = "false")
                                                Boolean bg2)
      throws IOException {
    String portraitId = fileService.saveFile(image);
    Companion companion = new Companion();
    logger.info("alignment - {}", alignment);
    Optional<Kit> optionalKit1 = kitService.getKitById(kitId1);
    Kit kit1;
    kit1 = optionalKit1.orElse(null);
    Optional<Kit> optionalKit2 = kitService.getKitById(kitId2);
    Kit kit2;
    kit2 = optionalKit2.orElse(null);
    Optional<Kit> optionalKit3 = kitService.getKitById(kitId3);
    Kit kit3;
    kit3 = optionalKit3.orElse(null);
    companion.setName(name);
    companion.setPortraitId(portraitId);
    companion.setAlignment(alignment);
    companion.setStrength(str);
    companion.setDexterity(dex);
    companion.setConstitution(con);
    companion.setIntelligence(inT);
    companion.setWisdom(wis);
    companion.setCharisma(cha);
    companion.setCharClassId(createClass(kit1, kit1lvl, kit2, kit2lvl, kit3, kit3lvl));
    companion.setBg1(bg1);
    companion.setSod(sod);
    companion.setBg2(bg2);
    Companion savedCompanion = companionService.addCompanion(companion);
    logger.info("Character {} created", savedCompanion.getName());
    return ResponseEntity.status(201).body(savedCompanion);
  }

  @GetMapping("/portrait/{id}")
  public ResponseEntity<byte[]> getPortrait(@PathVariable String id) {
    try {
      byte[] file = fileService.getFile(id);
      return ResponseEntity.ok()
          .contentType(MediaType.IMAGE_PNG)
          .body(file);
    } catch (Exception e) {
      logger.error("Error retrieving portrait with id: {}", id, e);
      return ResponseEntity.status(500).body(null);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCompanion(@PathVariable String id) {
    companionService.deleteCompanion(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/class/{id}")
  public ResponseEntity<String> getClassById(@PathVariable String id) {
    Class charClass;
    if (classService.getClassById(id).isPresent()) {
      charClass = classService.getClassById(id).get();
    } else {
      throw new IllegalArgumentException("No Class with this ID");
    }
    return Optional.of(charClass.toString()).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  private String createClass(Kit kit1, Integer kit1lvl, Kit kit2, Integer kit2lvl, Kit kit3,
                             Integer kit3lvl) {
    Class charClass = new Class();
    logger.info("Kit 1 {}, Kit 2 {} , Kit 3 {}", kit1, kit2, kit3);
    HashMap<Kit, Integer> kitLevels = new HashMap<>();
    charClass.setMultiClass(false);
    charClass.setDualClass(false);
    if (kit1lvl != 0 || kit2lvl != 0 || kit3lvl != 0) {
      charClass.setDualClass(true);
    }
    if (kit3 != null) {
      kitLevels.put(kit3, kit3lvl);
      charClass.setMainKit(kit3);
    }
    if (kit2 != null) {
      kitLevels.put(kit2, kit2lvl);
      charClass.setMainKit(kit2);
    }
    if (kit1 != null) {
      kitLevels.put(kit1, kit1lvl);
      charClass.setMainKit(kit1);
    }
    if (kitLevels.keySet().size() != 1) {
      charClass.setMultiClass(true);
    }
    charClass.setKitLevels(kitLevels);
    Class savedCharClass = classService.addClass(charClass);
    logger.info("Character class set to {}", savedCharClass.toString());
    return savedCharClass.getId();
  }
}
