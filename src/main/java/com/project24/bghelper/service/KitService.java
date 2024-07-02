package com.project24.bghelper.service;

import com.project24.bghelper.model.Kit;
import com.project24.bghelper.repository.KitRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KitService {

  private static final Logger logger = LoggerFactory.getLogger(KitService.class);

  private KitRepository kitRepository;

  @Autowired
  public KitService(KitRepository kitRepository) {
    this.kitRepository = kitRepository;
  }

  public List<Kit> getAllKits() {
    return kitRepository.findAll();
  }

  public Optional<Kit> getKitById(String id) {
    return kitRepository.findById(id);
  }

  public Kit addKit(Kit kit) {
    return kitRepository.save(kit);
  }

  public void deleteKit(String id) {
    kitRepository.deleteById(id);
    logger.info("Deleted Kit with ID: {}", id);
  }
}
