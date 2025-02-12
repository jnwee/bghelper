package controller;

import com.jnwee.backend.BackendApplication;
import com.jnwee.backend.controller.CharacterController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

@WebMvcTest(CharacterController.class)
@ContextConfiguration(classes = BackendApplication.class)
public class CharacterControllerTest {
    /*
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CharacterService service;

    @MockitoBean
    private CharacterRepository repository;

    @Test
    public void testGetAllCharacters() throws Exception {
        // Arrange: Mock service response
        Char blackbeard = new Char("Blackbeard");
        Char jackSparrow = new Char("Jack Sparrow");

        when(service.getAllCharactersLightweight()).thenReturn(
            Arrays.asList(blackbeard, jackSparrow)
        );

        // Act & Assert
        mockMvc
            .perform(get("/api/characters/lightweight"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Blackbeard"))
            .andExpect(jsonPath("$[1].name").value("Jack Sparrow"));
    }

    @Test
    public void testGetCharactersSortedByCreatedAt() throws Exception {
        Char char1 = new Char("Blackbeard");
        Char char2 = new Char("Jack Sparrow");
        char2.setCreatedAt(char1.getCreatedAt().minusDays(1));

        when(service.getAllCharactersLightweight()).thenReturn(
            Arrays.asList(char1, char2)
        );

        mockMvc
            .perform(get("/api/characters/lightweight"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Blackbeard"))
            .andExpect(jsonPath("$[1].name").value("Jack Sparrow"));
    }
    */
}
