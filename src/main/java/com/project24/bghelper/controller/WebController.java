package com.project24.bghelper.controller;

import com.project24.bghelper.model.Alignment;
import com.project24.bghelper.model.Character;
import com.project24.bghelper.model.Companion;
import com.project24.bghelper.model.Race;
import com.project24.bghelper.service.CharacterService;
import com.project24.bghelper.service.CompanionService;
import com.project24.bghelper.service.PartyService;
import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

  private CompanionService companionService;
  private CharacterService characterService;
  private PartyService partyService;

  public WebController(CompanionService companionService, CharacterService characterService,
                       PartyService partyService) {
    this.companionService = companionService;
    this.characterService = characterService;
    this.partyService = partyService;
  }

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/companions")
  public String viewCompanions(Model model) {
    model.addAttribute("companionsBg1", companionService.getCompanionsBg1());
    model.addAttribute("companionsBg2", companionService.getCompanionsBg2());
    return "companion-list";
  }

  @GetMapping("/companions/new")
  public String newCompanion(Model model) {
    model.addAttribute("alignments", Arrays.stream(Alignment.values()).toList());
    model.addAttribute("races", Arrays.stream(Race.values()).toList());
    return "createCompanion";
  }

  @GetMapping("companions/{id}")
  public String viewCompanion(Model model, @PathVariable String id) {
    if (companionService.getCompanionById(id).isPresent()) {
      model.addAttribute("companion", companionService.getCompanionById(id).get());
    }
    return "companion";
  }

  @GetMapping("/characters")
  public String viewCharacters(Model model) {
    model.addAttribute("aliveCharacters", characterService.getAliveCharacters().reversed());
    model.addAttribute("deadCharacters", characterService.getDeadCharacters());
    return "character-list";
  }

  @GetMapping("/characters/new")
  public String newCharacter(Model model) {
    model.addAttribute("alignments", Arrays.stream(Alignment.values()).toList());
    model.addAttribute("races", Arrays.stream(Race.values()).toList());
    return "createCharacter";
  }

  @GetMapping("/characters/{id}")
  public String viewCharacter(Model model, @PathVariable String id) {
    if (characterService.getCharacterById(id).isPresent()) {
      Character character = characterService.getCharacterById(id).get();
      model.addAttribute("character", character);
      model.addAttribute("partyBg1",
          partyService.getPartyById(character.getPartyBg1()).get().getParty());
      model.addAttribute("partyBg2",
          partyService.getPartyById(character.getPartyBg2()).get().getParty());
    }
    return "character";
  }
}
