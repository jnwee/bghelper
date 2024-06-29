package com.project24.bghelper.controller;

import com.project24.bghelper.model.Companion;
import com.project24.bghelper.service.CompanionService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * HTTP Controller for Companions.
 */
@RestController
@RequestMapping("/Home/Companions")
public class CompanionController {

  CompanionService companionService;

  public CompanionController(CompanionService companionService) {
    this.companionService = companionService;
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

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public Companion addCompanion(@RequestBody Companion companion) {
    return companionService.addCompanion(companion);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCompanion(@PathVariable String id) {
    companionService.deleteCompanion(id);
    return ResponseEntity.noContent().build();
  }
}
