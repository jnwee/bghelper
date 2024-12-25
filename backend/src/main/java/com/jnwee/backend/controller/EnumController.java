package com.jnwee.backend.controller;

import com.jnwee.backend.model.Alignment;
import com.jnwee.backend.model.CharacterClass;
import com.jnwee.backend.model.CompanionBg1;
import com.jnwee.backend.model.CompanionBg2;
import com.jnwee.backend.model.Race;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enums")
public class EnumController {

    @GetMapping("/classes")
    public ResponseEntity<List<String>> getCharacterClasses() {
        return ResponseEntity.ok(
            Arrays.stream(CharacterClass.values()).map(Enum::name).toList()
        );
    }

    @GetMapping("/races")
    public ResponseEntity<List<String>> getCharacterRaces() {
        return ResponseEntity.ok(
            Arrays.stream(Race.values()).map(Enum::name).toList()
        );
    }

    @GetMapping("/alignments")
    public ResponseEntity<List<String>> getCharacterAlignments() {
        return ResponseEntity.ok(
            Arrays.stream(Alignment.values()).map(Enum::name).toList()
        );
    }

    @GetMapping("/companions/bg1")
    public ResponseEntity<List<String>> getBG1Companions() {
        return ResponseEntity.ok(
            Arrays.stream(CompanionBg1.values()).map(Enum::name).toList()
        );
    }

    @GetMapping("/companions/bg2")
    public ResponseEntity<List<String>> getBG2Companions() {
        return ResponseEntity.ok(
            Arrays.stream(CompanionBg2.values()).map(Enum::name).toList()
        );
    }
}
