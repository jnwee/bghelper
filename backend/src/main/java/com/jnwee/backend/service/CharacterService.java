package com.jnwee.backend.service;

import com.jnwee.backend.model.Char;
import com.jnwee.backend.repository.CharacterRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    private final String imageDirectory = "images/";

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

    public String storeImage(String characterId, MultipartFile imageFile)
        throws IOException {
        // Create the directory if it doesn't exist
        File dir = new File(imageDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Normalize the file name
        String filename = StringUtils.cleanPath(
            imageFile.getOriginalFilename()
        );

        // Save the file locally
        Path filePath = Paths.get(
            imageDirectory + characterId + "_" + filename
        );
        Files.copy(
            imageFile.getInputStream(),
            filePath,
            StandardCopyOption.REPLACE_EXISTING
        );

        // Return the file path or URL (adjust if using external storage)
        return filePath.toString();
    }

    public Char updateCharacterImage(String id, String imageUrl) {
        Char character = getCharacterById(id);
        if (character != null) {
            character.setImageUrl(imageUrl);
            return characterRepository.save(character);
        }
        return null;
    }
}
