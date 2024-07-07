package com.project24.bghelper.controller;

import com.project24.bghelper.service.CompanionService;
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
  public String viewCompanions() {
    return "companion-list";
  }

  @GetMapping("/companions/new")
  public String newCompanion() {
    return "createCompanion";
  }

  @GetMapping("/kits")
  public String viewKits() {
    return "kit-list";
  }

  @GetMapping("/kits/new")
  public String newKit() {
    return "createKit";
  }

  @GetMapping("companions/{id}")
  public String viewCompanion(@PathVariable String id) {
    return "companion";
  }
}
