package com.example.projektmd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorController.class)
public class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // Jakiekolwiek przygotowania przed każdym testem
    }

    @Test
    public void testAdd() throws Exception {
        mockMvc.perform(get("/add")
                        .param("a", "10")
                        .param("b", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(15.0)));
    }

    @Test
    public void testSubtract() throws Exception {
        mockMvc.perform(get("/subtract")
                        .param("a", "10")
                        .param("b", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(5.0)));
    }

    @Test
    public void testMultiply() throws Exception {
        mockMvc.perform(get("/multiply")
                        .param("a", "10")
                        .param("b", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(50.0)));
    }

    @Test
    public void testDivide() throws Exception {
        mockMvc.perform(get("/divide")
                        .param("a", "10")
                        .param("b", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(2.0)));
    }

    @Test
    public void testDivideByZero() throws Exception {
        mockMvc.perform(get("/divide")
                        .param("a", "10")
                        .param("b", "0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", is("Dzielenie przez zero nie jest dozwolone.")));
    }
}
