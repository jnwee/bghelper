package controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnwee.backend.BackendApplication;
import com.jnwee.backend.controller.CharacterController;
import com.jnwee.backend.model.Char;
import com.jnwee.backend.repository.CharacterRepository;
import com.jnwee.backend.service.CharacterService;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CharacterController.class)
@ContextConfiguration(classes = BackendApplication.class)
public class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CharacterService service;

    @MockitoBean
    private CharacterRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllCharacters() throws Exception {
        // Arrange: Mock service response
        Char blackbeard = new Char("Blackbeard", true);
        Char jackSparrow = new Char("Jack Sparrow", false);

        when(service.getAllCharacters()).thenReturn(
            Arrays.asList(blackbeard, jackSparrow)
        );

        // Act & Assert
        mockMvc
            .perform(get("/api/characters"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Blackbeard"))
            .andExpect(jsonPath("$[0].dead").value(true))
            .andExpect(jsonPath("$[1].name").value("Jack Sparrow"))
            .andExpect(jsonPath("$[1].dead").value(false));
    }

    @Test
    public void testAddCharacter() throws Exception {
        // Arrange: Mock service response
        Char willTurner = new Char("Will Turner", false);
        when(service.createCharacter(Mockito.any(Char.class))).thenReturn(
            willTurner
        );

        // Act & Assert
        mockMvc
            .perform(
                post("/api/characters")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(willTurner))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Will Turner"))
            .andExpect(jsonPath("$.dead").value(false));
    }
}
