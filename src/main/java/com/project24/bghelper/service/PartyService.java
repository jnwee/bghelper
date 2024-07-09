package com.project24.bghelper.service;

import com.project24.bghelper.model.Party;
import com.project24.bghelper.repository.PartyRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PartyService {

  private PartyRepository partyRepository;

  public PartyService (PartyRepository partyRepository) {
    this.partyRepository = partyRepository;
  }

  public Optional<Party> getPartyById(String id) {
    return partyRepository.findById(id);
  }

  /**
   * should only be called when creating a character to create an empty party.
   * should also be deleted on character deletion
   * @return id to hand to character
   */
  public String createParty() {
    Party savedParty = partyRepository.save(new Party());
    return savedParty.getId();
  }

  public void deleteParty(String id) {
    partyRepository.deleteById(id);
  }
}
