package com.project24.bghelper.controller;

import com.project24.bghelper.model.Alignment;
import com.project24.bghelper.model.Companion;
import com.project24.bghelper.model.Race;
import com.project24.bghelper.service.CompanionService;
import com.project24.bghelper.service.FileService;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

  @Autowired
  public CompanionController(CompanionService companionService, FileService fileService) {
    this.companionService = companionService;
    this.fileService = fileService;
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
                                                @RequestParam("portrait") MultipartFile portrait,
                                                @RequestParam("race") String race,
                                                @RequestParam("alignment") String alignment,
                                                @RequestParam("charClass") String charClass,
                                                @RequestParam("strength") Integer strength,
                                                @RequestParam("dexterity") Integer dexterity,
                                                @RequestParam("constitution") Integer constitution,
                                                @RequestParam("intelligence") Integer intelligence,
                                                @RequestParam("wisdom") Integer wisdom,
                                                @RequestParam("charisma") Integer charisma,
                                                @RequestParam(value = "fighter", defaultValue = "false")
                                                Boolean fighter,
                                                @RequestParam(value = "thief", defaultValue = "false")
                                                Boolean thief,
                                                @RequestParam String mageType,
                                                @RequestParam String clericDruidType,
                                                @RequestParam(value = "bg1", defaultValue = "false")
                                                Boolean bg1,
                                                @RequestParam(value = "sod", defaultValue = "false")
                                                Boolean sod,
                                                @RequestParam(value = "bg2", defaultValue = "false")
                                                Boolean bg2)
      throws IOException {
    logger.info("Race - {}, Alignment - {}", race, alignment);
    Companion companion = new Companion();

    companion.setFighter(false);
    companion.setThief(false);
    companion.setFullMage(false);
    companion.setHalfMage(false);
    companion.setDruid(false);
    companion.setFullCleric(false);
    companion.setHalfCleric(false);

    companion.setName(name);
    companion.setRace(Race.fromString(race));
    companion.setAlignment(Alignment.fromString(alignment));
    String portraitId = fileService.saveFile(portrait);
    companion.setPortraitId(portraitId);
    companion.setCharClass(charClass);

    companion.setStrength(strength);
    companion.setDexterity(dexterity);
    companion.setConstitution(constitution);
    companion.setIntelligence(intelligence);
    companion.setWisdom(wisdom);
    companion.setCharisma(charisma);

    companion.setFighter(fighter);
    companion.setThief(thief);

    companion.setFullMage("fullMage".equals(mageType));
    companion.setHalfMage("halfMage".equals(mageType));

    companion.setDruid("druid".equals(clericDruidType));
    companion.setFullCleric("fullCleric".equals(clericDruidType));
    companion.setHalfCleric("halfCleric".equals(clericDruidType));

    companion.setBg1(bg1);
    companion.setSod(sod);
    companion.setBg2(bg2);

    Companion savedCompanion = companionService.addCompanion(companion);
    logger.info("Character {} created", savedCompanion.getName());
    companionService.sync();
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
    companionService.sync();
    return ResponseEntity.noContent().build();
  }
}
