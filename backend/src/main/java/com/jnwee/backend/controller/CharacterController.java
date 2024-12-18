package com.jnwee.backend.controller;

import com.jnwee.backend.model.Char;
import com.jnwee.backend.service.CharacterService;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/sorted")
    public List<Char> getCharactersSorted(
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
            ? Sort.Direction.ASC
            : Sort.Direction.DESC;
        return characterService.getAllCharactersSorted(sortBy, sortDirection);
    }

    @GetMapping("/lightweight")
    public List<Char> getLightweightCharacters() {
        return characterService.getAllCharactersLightweight();
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

    @PatchMapping("/{id}/die")
    public ResponseEntity<?> letCharacterDie(@PathVariable String id) {
        try {
            characterService.letCharacterDie(id);
            return ResponseEntity.ok("Character has died.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "An error occurred."
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable String id) {
        characterService.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload")
    public Char uploadImage(
        @PathVariable String id,
        @RequestParam("image") MultipartFile imageFile
    ) {
        try {
            String imageUrl = characterService.storeImage(id, imageFile);
            return characterService.updateCharacterImage(id, imageUrl);
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error uploading image",
                e
            );
        }
    }
}
