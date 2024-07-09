package com.project24.bghelper.controller;

import com.project24.bghelper.model.Alignment;
import com.project24.bghelper.model.Character;
import com.project24.bghelper.model.Race;
import com.project24.bghelper.service.CharacterService;
import com.project24.bghelper.service.FileService;
import com.project24.bghelper.service.PartyService;
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
@RequestMapping("/api/characters")
public class CharacterController {

  private static final Logger logger = LoggerFactory.getLogger(CompanionController.class);

  CharacterService characterService;
  FileService fileService;
  PartyService partyService;

  @Autowired
  public CharacterController(CharacterService characterService, FileService fileService,
                             PartyService partyService) {
    this.characterService = characterService;
    this.fileService = fileService;
    this.partyService = partyService;
  }

  @GetMapping("")
  public List<Character> getAllCompanions() {
    return characterService.getAllCharacters();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Character> getCompanionById(@PathVariable String id) {
    return characterService.getCharacterById(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/create")
  public ResponseEntity<Character> addCharacter(@RequestParam("name") String name,
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
                                                @RequestParam String clericDruidType)
      throws IOException {
    Character character = new Character();

    character.setFighter(false);
    character.setThief(false);
    character.setFullMage(false);
    character.setHalfMage(false);
    character.setDruid(false);
    character.setFullCleric(false);
    character.setHalfCleric(false);

    character.setName(name);
    character.setRace(Race.fromString(race));
    character.setAlignment(Alignment.fromString(alignment));
    String portraitId = fileService.saveFile(portrait);
    character.setPortraitId(portraitId);
    character.setCharClass(charClass);

    character.setStrength(strength);
    character.setDexterity(dexterity);
    character.setConstitution(constitution);
    character.setIntelligence(intelligence);
    character.setWisdom(wisdom);
    character.setCharisma(charisma);

    character.setFighter(fighter);
    character.setThief(thief);

    character.setFullMage("fullMage".equals(mageType));
    character.setHalfMage("halfMage".equals(mageType));

    character.setDruid("druid".equals(clericDruidType));
    character.setFullCleric("fullCleric".equals(clericDruidType));
    character.setHalfCleric("halfCleric".equals(clericDruidType));

    character.setPartyBg1(partyService.createParty(false));
    character.setPartyBg2(partyService.createParty(true));

    character.setAlive(true);

    Character savedCharacter = characterService.addCharacter(character);
    logger.info("Character {} created", savedCharacter.getName());
    return ResponseEntity.status(201).body(savedCharacter);
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
    characterService.deleteCharacter(id);
    return ResponseEntity.noContent().build();
  }
}
