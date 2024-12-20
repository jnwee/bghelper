package com.jnwee.backend.service;

import com.jnwee.backend.model.Char;
import com.jnwee.backend.model.Progress;
import com.jnwee.backend.model.Status;
import com.jnwee.backend.repository.CharacterRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CharacterService {

    private static final Logger logger = LoggerFactory.getLogger(
        CharacterService.class
    );

    private final CharacterRepository characterRepository;

    private final String imageDirectory = "/data/images/";

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<Char> getCharactersByProgress(Progress progress) {
        String sortBy = "createdAt";
        return characterRepository.findByProgress(
            progress,
            Status.DEAD,
            Sort.by(Direction.DESC, sortBy)
        );
    }

    public List<Char> getCharactersByStatus(Status status) {
        String sortBy = "createdAt";
        if (status == Status.DEAD) {
            sortBy = "diedAt";
        }
        return characterRepository.findByStatus(
            status,
            Sort.by(Direction.DESC, sortBy)
        );
    }

    public int[] getCharacterCircleChartStats() {
        int diedInBg1 = characterRepository
            .findByProgress(Progress.BG1, Status.DEAD)
            .size();
        int diedInBg2 = characterRepository
            .findByProgress(Progress.BG2, Status.DEAD)
            .size();
        int diedInTob = characterRepository
            .findByProgress(Progress.TOB, Status.DEAD)
            .size();
        int ascended = characterRepository.findByStatus(Status.ASCENDED).size();

        int[] stats = new int[4];

        stats[0] = diedInBg1;
        stats[1] = diedInBg2;
        stats[2] = diedInTob;
        stats[3] = ascended;

        return stats;
    }

    public Char createCharacter(Char character) {
        return characterRepository.save(character);
    }

    public Char getCharacterById(String id) {
        return characterRepository.findById(id).orElse(null);
    }

    public void letCharacterDie(String id) {
        Char character = characterRepository
            .findById(id)
            .orElseThrow(() ->
                new IllegalArgumentException("Character not found")
            );
        if (character.getStatus() == Status.ALIVE) {
            character.setStatus(Status.DEAD);
            character.setDiedAt(LocalDateTime.now());
            characterRepository.save(character);
        } else {
            throw new IllegalArgumentException(
                "Character is not ALIVE and therefore can't die"
            );
        }
    }

    public void deleteCharacter(String id) {
        try {
            getCharacterImage(id).getFile().delete();
            logger.info("Image of character with id:" + id + " deleted");
        } catch (IOException e) {
            logger.info(
                "Image of character with id:" + id + " couldn't be deleted"
            );
        }
        characterRepository.deleteById(id);
        logger.info("Character with id:" + id + " deleted");
    }

    //============== Characterportrait-related Methods ======================

    public String storeImage(String characterId, MultipartFile imageFile)
        throws IOException {
        // Create the directory if it doesn't exist
        File dir = new File(imageDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Generate unique file name
        String filename =
            characterId +
            "_" +
            StringUtils.cleanPath(imageFile.getOriginalFilename());
        Path filePath = Paths.get(imageDirectory + filename);

        // Save the file
        Files.copy(
            imageFile.getInputStream(),
            filePath,
            StandardCopyOption.REPLACE_EXISTING
        );

        System.out.println("Image stored at: " + filePath.toString()); // Debug log

        // Save only the file name in the database
        return filename; // Not the absolute path
    }

    public void updateCharacterImage(String id, String imageUrl) {
        Char character = characterRepository
            .findById(id)
            .orElseThrow(() ->
                new IllegalArgumentException("Character not found")
            );
        logger.info("Updated character image URL: " + imageUrl); // Debug log
        character.setImageUrl(imageUrl);
        characterRepository.save(character);
    }

    public Resource getCharacterImage(String characterId)
        throws FileNotFoundException {
        Char character = characterRepository
            .findById(characterId)
            .orElseThrow(() ->
                new IllegalArgumentException("Character not found")
            );

        String filename = character.getImageUrl();
        System.out.println("Character image filename: " + filename); // Debug log

        // Construct the path to the image
        Path imagePath = Paths.get(imageDirectory + filename);
        System.out.println("Attempting to retrieve image from: " + imagePath); // Debug log

        if (!Files.exists(imagePath)) {
            throw new FileNotFoundException(
                "Image not found for character ID: " + characterId
            );
        }

        return new FileSystemResource(imagePath);
    }
}
