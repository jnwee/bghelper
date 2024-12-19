package com.jnwee.backend.controller;

import com.jnwee.backend.model.Char;
import com.jnwee.backend.model.Status;
import com.jnwee.backend.service.CharacterService;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Char>> getCharsByStatus(
        @RequestParam Status status
    ) {
        List<Char> filteredChars = characterService.getCharsByStatus(status);
        return ResponseEntity.ok(filteredChars);
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
            Map<String, String> response = new HashMap<>();
            response.put("message", "Character marked as dead");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "An error occurred."
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCharacter(
        @PathVariable String id
    ) {
        characterService.deleteCharacter(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Character deleted successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to upload an image for a character
     */
    @PostMapping("/{id}/upload")
    public ResponseEntity<?> uploadImage(
        @PathVariable String id,
        @RequestParam("image") MultipartFile imageFile
    ) {
        try {
            String imageUrl = characterService.storeImage(id, imageFile);
            characterService.updateCharacterImage(id, imageUrl);
            return ResponseEntity.ok("Image uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "Failed to upload image: " + e.getMessage()
            );
        }
    }

    /**
     * Endpoint to fetch an image for a character
     */
    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> getImage(@PathVariable String id) {
        try {
            Resource image = characterService.getCharacterImage(id);
            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Adjust as needed
                .body(image);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
