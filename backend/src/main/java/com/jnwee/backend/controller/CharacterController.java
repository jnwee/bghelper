package com.jnwee.backend.controller;

import com.jnwee.backend.model.Char;
import com.jnwee.backend.model.Progress;
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
    public ResponseEntity<List<Char>> getCharactersByStatus(
        @RequestParam(required = false) Status status,
        @RequestParam(required = false) Progress progress
    ) {
        // TODO: Rework this to works if status && progress != null
        if (status != null) {
            List<Char> filteredChars = characterService.getCharactersByStatus(
                status
            );
            return ResponseEntity.ok(filteredChars);
        } else if (progress != null) {
            List<Char> filteredChars = characterService.getCharactersByProgress(
                progress
            );
            return ResponseEntity.ok(filteredChars);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<int[]> getCharacterStats() {
        return ResponseEntity.ok(
            characterService.getCharacterCircleChartStats()
        );
    }

    @PostMapping
    public ResponseEntity<?> createCharacter(@RequestBody Char character) {
        try {
            return ResponseEntity.ok(
                characterService.createCharacter(character)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Char> getCharacterById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(characterService.getCharacterById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
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

    @PatchMapping("/{id}/advance")
    public ResponseEntity<?> advanceCharacterProgress(@PathVariable String id) {
        try {
            // TODO
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                e
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
