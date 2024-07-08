package com.project24.bghelper.service;

import com.project24.bghelper.model.Alignment;
import com.project24.bghelper.model.Companion;
import com.project24.bghelper.repository.CompanionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanionService {

  private final static Logger logger = LoggerFactory.getLogger(CompanionService.class);

  private CompanionRepository companionRepository;

  private List<Companion> goodCompanions;
  private List<Companion> neutralCompanions;
  private List<Companion> evilCompanions;

  @Autowired
  public CompanionService(CompanionRepository companionRepository) {
    this.companionRepository = companionRepository;
    goodCompanions = new ArrayList<>();
    neutralCompanions = new ArrayList<>();
    evilCompanions = new ArrayList<>();

    sync();
  }

  public List<Companion> getAllCompanions() {
    return companionRepository.findAll();
  }

  public Optional<Companion> getCompanionById(String id) {
    return companionRepository.findById(id);
  }

  public Companion addCompanion(Companion companion) {
    return companionRepository.save(companion);
  }

  public void deleteCompanion(String id) {
    companionRepository.deleteById(id);
    logger.info("Deleted Companion with ID - {}", id);
  }

  public List<Companion> getGoodCompanions() {
    return goodCompanions;
  }

  public List<Companion> getNeutralCompanions() {
    return neutralCompanions;
  }

  public List<Companion> getEvilCompanions() {
    return neutralCompanions;
  }

  /**
   * synchronizes the Lists for good, neutral & evil Companions.
   * more efficient than creating a new List on every get I'd say
   */
  public void sync() {
    goodCompanions.clear();
    neutralCompanions.clear();
    evilCompanions.clear();

    List<Companion> allCompanions = companionRepository.findAll();
    for (Companion companion : allCompanions) {
      if (companion.getAlignment() == Alignment.CHAOTIC_EVIL ||
          companion.getAlignment() == Alignment.NEUTRAL_EVIL ||
          companion.getAlignment() == Alignment.LAWFUL_EVIL) {
        evilCompanions.add(companion);
      }
      if (companion.getAlignment() == Alignment.CHAOTIC_NEUTRAL ||
          companion.getAlignment() == Alignment.NEUTRAL ||
          companion.getAlignment() == Alignment.LAWFUL_NEUTRAL) {
        neutralCompanions.add(companion);
      }
      if (companion.getAlignment() == Alignment.CHAOTIC_GOOD ||
          companion.getAlignment() == Alignment.NEUTRAL_GOOD ||
          companion.getAlignment() == Alignment.LAWFUL_GOOD) {
        goodCompanions.add(companion);
      }
    }
  }
}
