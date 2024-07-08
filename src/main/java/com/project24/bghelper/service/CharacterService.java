package com.project24.bghelper.service;

import com.project24.bghelper.model.Character;
import com.project24.bghelper.repository.CharacterRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

  Logger logger = LoggerFactory.getLogger(CharacterService.class);

  private CharacterRepository characterRepository;

  private List<Character> aliveCharacters;
  private List<Character> deadCharacters;

  public CharacterService(CharacterRepository characterRepository) {
    this.characterRepository = characterRepository;

    aliveCharacters = new ArrayList<>();
    deadCharacters = new ArrayList<>();

    sync();
  }

  public List<Character> getAllCharacters() {
    return characterRepository.findAll();
  }

  public Optional<Character> getCharacterById(String id) {
    return characterRepository.findById(id);
  }

  public Character addCharacter(Character character) {
    return characterRepository.save(character);
  }

  public void deleteCharacter(String id) {
    characterRepository.deleteById(id);
    logger.info("Deleted Character with ID - {}", id);
  }

  public List<Character> getAliveCharacters() {
    return aliveCharacters;
  }

  public List<Character> getDeadCharacters() {
    return deadCharacters;
  }

  private void sync() {
    aliveCharacters.clear();
    deadCharacters.clear();

    List<Character> allCharacters = characterRepository.findAll();

    for (Character character : allCharacters) {
      if (character.isAlive()) {
        aliveCharacters.add(character);
      } else {
        deadCharacters.add(character);
      }
    }
  }
}
