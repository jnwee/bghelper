package com.project24.bghelper.controller;

import com.project24.bghelper.service.CompanionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
  public String newCompanion() {
    return "createCompanion";
  }
}
