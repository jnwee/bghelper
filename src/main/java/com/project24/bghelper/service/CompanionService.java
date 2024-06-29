package com.project24.bghelper.service;

import com.project24.bghelper.model.Companion;
import com.project24.bghelper.repository.CompanionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanionService {

  private CompanionRepository companionRepository;

  public CompanionService(CompanionRepository companionRepository) {
    this.companionRepository = companionRepository;
  }

  public List<Companion> getAllCompanions() {
    return companionRepository.findAll();
  }

  public Optional<Companion> getCompanionById(String id) {
    return companionRepository.findById(id);
  }

  // TODO reevaluate at some point if you want to pass an object
  public Companion addCompanion(Companion companion) {
    return companionRepository.save(companion);
  }

  public void deleteCompanion(String id) {
    companionRepository.deleteById(id);
  }
}
