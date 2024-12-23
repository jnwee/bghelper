package com.jnwee.backend.controller;

import com.jnwee.backend.model.CharacterClass;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enums")
public class EnumController {

    @GetMapping("/classes")
    public List<String> getCharacterClasses() {
        return Arrays.stream(CharacterClass.values()).map(Enum::name).toList();
    }
}
