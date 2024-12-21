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
        logger.info(
            "Characters filtered and fetched with progress: " +
            progress.toString()
        );

        String sortBy = "createdAt";
        return characterRepository.findByProgress(
            progress,
            Status.DEAD,
            Sort.by(Direction.DESC, sortBy)
        );
    }

    public List<Char> getCharactersByStatus(Status status) {
        logger.info(
            "Characters filtered and fetched by status: " + status.toString()
        );

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

        logger.info("Prepared statistics for circle charts: " + stats);

        return stats;
    }

    public String createCharacter(Char character) {
        logger.info("New Character created");
        try {
            characterRepository.save(character);
            return (
                "Character " + character.getName() + " was saved successfully"
            );
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public Char getCharacterById(String id) {
        logger.info("Fetching Character with ID: " + id);
        return characterRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Character not found"));
    }

    public String letCharacterDie(String id) {
        logger.info("Let Character with ID: " + id);
        Char character = characterRepository
            .findById(id)
            .orElseThrow(() ->
                new IllegalArgumentException("Character not found")
            );
        try {
            character.setStatus(Status.DEAD);
            characterRepository.save(character);
            return character.getName() + " is now marked dead";
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String advanceCharacter(String id) {
        Char character = characterRepository
            .findById(id)
            .orElseThrow(() ->
                new IllegalArgumentException("Character not found")
            );
        try {
            character.increaseProgress();
            characterRepository.save(character);
            return (
                character.getName() + " was successfully progressed by one step"
            );
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String deleteCharacter(String id) {
        logger.info("Delete Character with ID: " + id);
        try {
            getCharacterImage(id).getFile().delete();
            logger.info("Image of character with id: " + id + " deleted");
        } catch (IOException e) {
            logger.info(
                "Image of character with id:" + id + " couldn't be deleted"
            );
        }
        characterRepository.deleteById(id);
        return ("Character with id:" + id + " deleted");
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
