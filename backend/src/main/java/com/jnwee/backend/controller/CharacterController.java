package com.jnwee.backend.controller;

import com.jnwee.backend.model.Char;
import com.jnwee.backend.service.CharacterService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public List<Char> getAllCharacters() {
        return characterService.getAllCharacters();
    }

    @PostMapping
    public Char createCharacter(@RequestBody Char character) {
        return characterService.createCharacter(character);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Char> getCharacterById(@PathVariable String id) {
        Char character = characterService.getCharacterById(id);
        if (character == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(character);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable String id) {
        characterService.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }
}
