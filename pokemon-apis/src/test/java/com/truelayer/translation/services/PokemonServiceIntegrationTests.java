package com.truelayer.translation.services;

import com.truelayer.translation.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class PokemonServiceIntegrationTests {

    private RestTemplate restTemplate;

    private IPokemonService pokemonService;

    @BeforeEach
    void beforeEach() {
        var baseUrl = "https://pokeapi.co/api/v2";
        restTemplate = new RestTemplate();
        pokemonService = new PokemonService(baseUrl, restTemplate);
    }

    @Test
    @DisplayName("when getPokemonInformation method is called with valid name e.g. wormadam then response name should be same as input name")
    void test() throws Exception {
        var expectedName = "wormadam";
        var result = pokemonService.getPokemonInformation("wormadam");
        assertEquals(expectedName, result.getName());
    }

    @Test
    @DisplayName("when getPokemonInformation method is called with invalid name then response should be null")
    void test5() {
        assertThrows(ResourceNotFoundException.class, () -> pokemonService.getPokemonInformation(UUID.randomUUID().toString()));
    }

}
