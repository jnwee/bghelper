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

  private List<Companion> companionsBg1;
  private List<Companion> companionsBg2;
  private List<Companion> goodCompanionsBg1;
  private List<Companion> neutralCompanionsBg1;
  private List<Companion> evilCompanionsBg1;
  private List<Companion> goodCompanionsBg2;
  private List<Companion> neutralCompanionsBg2;
  private List<Companion> evilCompanionsBg2;

  @Autowired
  public CompanionService(CompanionRepository companionRepository) {
    this.companionRepository = companionRepository;
    companionsBg1 = new ArrayList<>();
    companionsBg2 = new ArrayList<>();
    goodCompanionsBg1 = new ArrayList<>();
    neutralCompanionsBg1 = new ArrayList<>();
    evilCompanionsBg1 = new ArrayList<>();
    goodCompanionsBg2 = new ArrayList<>();
    neutralCompanionsBg2 = new ArrayList<>();
    evilCompanionsBg2 = new ArrayList<>();

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

  public List<Companion> getGoodCompanionsBg1() {
    return goodCompanionsBg1;
  }

  public List<Companion> getNeutralCompanionsBg1() {
    return neutralCompanionsBg1;
  }

  public List<Companion> getEvilCompanionsBg1() {
    return neutralCompanionsBg1;
  }

  public List<Companion> getEvilCompanionsBg2() {
    return evilCompanionsBg2;
  }

  public List<Companion> getNeutralCompanionsBg2() {
    return neutralCompanionsBg2;
  }

  public List<Companion> getGoodCompanionsBg2() {
    return goodCompanionsBg2;
  }

  public List<Companion> getCompanionsBg2() {
    return companionsBg2;
  }

  public List<Companion> getCompanionsBg1() {
    return companionsBg1;
  }

  /**
   * synchronizes the Lists for good, neutral & evil Companions.
   * more efficient than creating a new List on every get I'd say
   */
  public void sync() {
    goodCompanionsBg1.clear();
    neutralCompanionsBg1.clear();
    evilCompanionsBg1.clear();
    goodCompanionsBg2.clear();
    neutralCompanionsBg2.clear();
    evilCompanionsBg2.clear();

    setCompanionListsDifferentTitles();
    setAlignmentLists(companionsBg1, false);
    setAlignmentLists(companionsBg2, true);
  }

  private void setCompanionListsDifferentTitles() {
    companionsBg1.clear();
    companionsBg2.clear();

    List<Companion> allCompanions = companionRepository.findAll();

    for (Companion companion : allCompanions) {
      if (companion.isBg1()) {
        companionsBg1.add(companion);
      }
      if (companion.isBg2()) {
        companionsBg2.add(companion);
      }
    }
  }

  /**
   * clean code I guess.
   *
   * @param companions List to split into alignments
   * @param bg         set to true for bg2, false for bg1 & SoD idc
   */
  private void setAlignmentLists(List<Companion> companions, boolean bg) {
    for (Companion companion : companions) {
      if (companion.getAlignment() == Alignment.CHAOTIC_EVIL ||
          companion.getAlignment() == Alignment.NEUTRAL_EVIL ||
          companion.getAlignment() == Alignment.LAWFUL_EVIL) {
        boolean why = (bg) ? evilCompanionsBg2.add(companion) : evilCompanionsBg1.add(companion);
      }
      if (companion.getAlignment() == Alignment.CHAOTIC_NEUTRAL ||
          companion.getAlignment() == Alignment.NEUTRAL ||
          companion.getAlignment() == Alignment.LAWFUL_NEUTRAL) {
        boolean why =
            (bg) ? neutralCompanionsBg2.add(companion) : neutralCompanionsBg1.add(companion);
      }
      if (companion.getAlignment() == Alignment.CHAOTIC_GOOD ||
          companion.getAlignment() == Alignment.NEUTRAL_GOOD ||
          companion.getAlignment() == Alignment.LAWFUL_GOOD) {
        boolean why = (bg) ? goodCompanionsBg2.add(companion) : goodCompanionsBg1.add(companion);
      }
    }
  }
}
