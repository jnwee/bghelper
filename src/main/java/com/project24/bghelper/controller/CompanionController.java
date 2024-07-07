package com.project24.bghelper.controller;

import com.project24.bghelper.model.Alignment;
import com.project24.bghelper.model.Companion;
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
                                                @RequestParam("portrait") MultipartFile image,
                                                @RequestParam("alignment") Alignment alignment,
                                                @RequestParam("str") Integer str,
                                                @RequestParam("dex") Integer dex,
                                                @RequestParam("con") Integer con,
                                                @RequestParam("int") Integer inT,
                                                @RequestParam("wis") Integer wis,
                                                @RequestParam("cha") Integer cha,
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
    companion.setName(name);
    companion.setPortraitId(portraitId);
    companion.setAlignment(alignment);
    companion.setStrength(str);
    companion.setDexterity(dex);
    companion.setConstitution(con);
    companion.setIntelligence(inT);
    companion.setWisdom(wis);
    companion.setCharisma(cha);
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
}
