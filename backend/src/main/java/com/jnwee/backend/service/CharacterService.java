package com.jnwee.backend.service;

import com.jnwee.backend.model.Char;
import com.jnwee.backend.repository.CharacterRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<Char> getAllCharacters() {
        return characterRepository.findAll();
    }

    public Char createCharacter(Char character) {
        return characterRepository.save(character);
    }

    public Char getCharacterById(String id) {
        return characterRepository.findById(id).orElse(null);
    }

    public void deleteCharacter(String id) {
        characterRepository.deleteById(id);
    }
}
