package com.project24.bghelper.controller;

import com.project24.bghelper.model.Alignment;
import com.project24.bghelper.model.Companion;
import com.project24.bghelper.model.Race;
import com.project24.bghelper.service.CompanionService;
import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

  private CompanionService companionService;

  public WebController(CompanionService companionService) {
    this.companionService = companionService;
  }

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/companions")
  public String viewCompanions(Model model) {
    model.addAttribute("companions", companionService.getAllCompanions());
    return "companion-list";
  }

  @GetMapping("/companions/new")
  public String newCompanion(Model model) {
    model.addAttribute("companion", new Companion());
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
}
