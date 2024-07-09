package com.project24.bghelper.service;

import com.project24.bghelper.model.Character;
import com.project24.bghelper.model.Companion;
import com.project24.bghelper.model.Party;
import com.project24.bghelper.repository.PartyRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PartyService {

  private static final Logger logger = LoggerFactory.getLogger(PartyService.class);

  private PartyRepository partyRepository;
  private CompanionService companionService;

  public PartyService(PartyRepository partyRepository, CompanionService companionService) {
    this.partyRepository = partyRepository;
    this.companionService = companionService;
  }

  public Optional<Party> getPartyById(String id) {
    return partyRepository.findById(id);
  }

  // TODO A LOT TO OPTIMIZE HERE :/
  public void fillPartyBalanced(Character character, boolean bg2, boolean good, boolean neutral,
                                boolean evil) {
    // create a List of companions suiting the filters
    List<Companion> suitedCompanions = new ArrayList<>();
    if (!bg2) {
      if (good) {
        suitedCompanions.addAll(companionService.getGoodCompanionsBg1());
      }
      if (neutral) {
        suitedCompanions.addAll(companionService.getNeutralCompanionsBg1());
      }
      if (evil) {
        suitedCompanions.addAll(companionService.getEvilCompanionsBg1());
      }
    }
    if (bg2) {
      if (good) {
        suitedCompanions.addAll(companionService.getGoodCompanionsBg2());
      }
      if (neutral) {
        suitedCompanions.addAll(companionService.getNeutralCompanionsBg2());
      }
      if (evil) {
        suitedCompanions.addAll(companionService.getEvilCompanionsBg2());
      }
    }
    //---------------------------------------------------------------------

    boolean thief = false;
    boolean fighter = false;
    int mage = 0;
    int cleric = 0;
    boolean druid = false;
    int size = 0;

    if (character.isFighter()) {
      fighter = true;
    }
    if (character.isThief()) {
      thief = true;
    }
    if (character.isHalfMage()) {
      mage++;
    }
    if (character.isFullMage()) {
      mage += 2;
    }
    if (character.isHalfCleric()) {
      cleric++;
    }
    if (character.isDruid()) {
      druid = true;
      cleric++;
    }
    if (character.isFullCleric()) {
      cleric += 2;
    }
    size++;
    logger.info(
        "Character {} added to calculation, composition is now - fighter = {}, thief = {}, mages = {}, clerics = {}, druid = {}, size = {}",
        character.getName(), fighter, thief, mage, cleric, druid, size);

    List<Companion> party = new ArrayList<>();
    if (!bg2) {
      Companion companion = null;
      while (size < 6) {
        if (companion != null) {
          if (companion.isFighter()) {
            fighter = true;
          }
          if (companion.isThief()) {
            thief = true;
          }
          if (companion.isHalfMage()) {
            mage++;
          }
          if (companion.isFullMage()) {
            mage += 2;
          }
          if (companion.isHalfCleric()) {
            cleric++;
          }
          if (companion.isDruid()) {
            druid = true;
            cleric++;
          }
          if (companion.isFullCleric()) {
            cleric += 2;
          }
          size++;
          suitedCompanions.remove(companion);
          logger.info(
              "Character {} added to calculation, composition is now - fighter = {}, thief = {}, mages = {}, clerics = {}, druid = {}, size = {}",
              companion.getName(), fighter, thief, mage, cleric, druid, size);
          party.add(companion);
        }

        companion = getRandomCompanion(suitedCompanions);

        if (!fighter) {
          companion = getRandomFighter(suitedCompanions);
        }
        if (cleric < 1 || cleric < 2 && druid == true) {
          companion = getRandomCleric(suitedCompanions);
        }
        if (!thief) {
          companion = getRandomThief(suitedCompanions);
        }
        while (mage < 2) {
          companion = getRandomMage(suitedCompanions);
        }
      }
      partyRepository.findById(character.getPartyBg1()).get().setParty(party);
    } else if (bg2) {
      Companion companion = null;
      while (size < 6) {
        if (companion != null) {
          if (companion.isFighter()) {
            fighter = true;
          }
          if (companion.isThief()) {
            thief = true;
          }
          if (companion.isHalfMage()) {
            mage++;
          }
          if (companion.isFullMage()) {
            mage += 2;
          }
          if (companion.isHalfCleric()) {
            cleric++;
          }
          if (companion.isDruid()) {
            druid = true;
            cleric++;
          }
          if (companion.isFullCleric()) {
            cleric += 2;
          }
          size++;
          suitedCompanions.remove(companion);
          logger.info(
              "Character {} added to calculation, composition is now - fighter = {}, thief = {}, mages = {}, clerics = {}, druid = {}, size = {}",
              companion.getName(), fighter, thief, mage, cleric, druid, size);
          party.add(companion);
        }

        companion = getRandomCompanion(suitedCompanions);

        if (!fighter) {
          companion = getRandomFighter(suitedCompanions);
        }
        if (cleric < 1 || cleric < 2 && druid == true) {
          companion = getRandomCleric(suitedCompanions);
        }
        if (!thief) {
          companion = getRandomThief(suitedCompanions);
        }
        while (mage < 2) {
          companion = getRandomMage(suitedCompanions);
        }
      }
      partyRepository.findById(character.getPartyBg2()).get().setParty(party);
    }
  }

  /**
   * Recursion for shits and giggles.
   *
   * @param suitedCompanions list
   * @return companion
   */
  private Companion getRandomMage(List<Companion> suitedCompanions) {
    Random random = new Random();
    Companion companion = suitedCompanions.get(random.nextInt(suitedCompanions.size()));
    if (!companion.isHalfMage() || !companion.isFullMage()) {
      suitedCompanions.remove(companion);
      getRandomMage(suitedCompanions);
    }
    return companion;
  }

  private Companion getRandomThief(List<Companion> suitedCompanions) {
    Random random = new Random();
    Companion companion = suitedCompanions.get(random.nextInt(suitedCompanions.size()));
    if (!companion.isThief()) {
      suitedCompanions.remove(companion);
      getRandomThief(suitedCompanions);
    }
    return companion;
  }

  private Companion getRandomCleric(List<Companion> suitedCompanions) {
    Random random = new Random();
    Companion companion = suitedCompanions.get(random.nextInt(suitedCompanions.size()));
    if (!companion.isFullCleric() || !companion.isHalfCleric()) {
      suitedCompanions.remove(companion);
      getRandomThief(suitedCompanions);
    }
    return companion;
  }

  private Companion getRandomFighter(List<Companion> suitedCompanions) {
    Random random = new Random();
    Companion companion = suitedCompanions.get(random.nextInt(suitedCompanions.size()));
    if (!companion.isFighter()) {
      suitedCompanions.remove(companion);
      getRandomFighter(suitedCompanions);
    }
    return companion;
  }

  private Companion getRandomCompanion(List<Companion> suitedCompanions) {
    Random random = new Random();
    Companion companion = suitedCompanions.get(random.nextInt(suitedCompanions.size()));
    return companion;
  }

  /**
   * should only be called when creating a character to create an empty party.
   * should also be deleted on character deletion
   *
   * @param bg2 true if the party is for bg2
   * @return id to hand to character
   */
  public String createParty(boolean bg2) {
    Party party = new Party();
    party.setBg2(bg2);
    Party savedParty = partyRepository.save(party);
    return savedParty.getId();
  }

  public void deleteParty(String id) {
    partyRepository.deleteById(id);
  }
}
