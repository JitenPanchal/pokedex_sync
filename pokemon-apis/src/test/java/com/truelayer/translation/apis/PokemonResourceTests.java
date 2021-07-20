package com.truelayer.translation.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.truelayer.translation.apis.models.PokemonInformationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PokemonResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("when getPokemonInformation method is called with valid name e.g. wormadam then response name should be same as input name")
    void test1() throws Exception {
        var result = mockMvc.perform(get("/pokemon/wormadam")).andDo(print()).andExpect(status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        var pokemonInformationDto = new ObjectMapper().readValue(json, PokemonInformationDto.class);
        assertEquals("wormadam", pokemonInformationDto.getName());
    }

    @Test
    @DisplayName("when getPokemonInformation method is called with invalid name then response should be null")
    void test2() throws Exception {
        mockMvc.perform(get("/pokemon/" + UUID.randomUUID().toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("when /pokemon/translated/wormadam method is called with valid name e.g. wormadam then response name should be same as input name")
    void test3() throws Exception {
        var result = mockMvc.perform(get("/pokemon/translated/wormadam")).andDo(print()).andExpect(status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        var pokemonInformationDto = new ObjectMapper().readValue(json, PokemonInformationDto.class);
        assertEquals("wormadam", pokemonInformationDto.getName());
    }

    @Test
    @DisplayName("when api /pokemon/translated/UUID.randomUUID().toString() is called with invalid name then response should be null")
    void test4() throws Exception {
        mockMvc.perform(get("/pokemon/translated/" + UUID.randomUUID().toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
