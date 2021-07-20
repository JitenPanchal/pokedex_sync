package com.truelayer.translation.services;


import com.truelayer.translation.services.exceptions.ResourceNotFoundException;
import com.truelayer.translation.services.models.pokemon.FlavorTextEntry;
import com.truelayer.translation.services.models.pokemon.Habitat;
import com.truelayer.translation.services.models.pokemon.PokemonInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class PokemonServiceUnitTests {

    private IPokemonService pokemonService;

    @BeforeEach
    void beforeEach() {
        var baseUrl = "https://pokeapi.co/api/v2";
        pokemonService = new PokemonService(baseUrl, new RestTemplate());
    }

    @Test
    @DisplayName("when getPokemonInformation method is called with null parameter then IllegalArgumentException should be thrown")
    void test1() {
        assertThrows(IllegalArgumentException.class, () -> pokemonService.getPokemonInformation(null));
    }

    @Test
    @DisplayName("when getPokemonInformation method is called with blank name parameter then IllegalArgumentException should be thrown")
    void test2() {
        assertThrows(IllegalArgumentException.class, () -> pokemonService.getPokemonInformation(""));
    }

    @Test
    @DisplayName("when getPokemonInformation method is called with valid name e.g. wormadam then response name should be same as input name")
    void test3() {

        var pokemonInformation = new PokemonInformation();
        var flavorTextEntries = new ArrayList<FlavorTextEntry>();

        var flavorTextEntry1 = new FlavorTextEntry();
        flavorTextEntry1.setFlavorText("wormadam description 1");

        var flavorTextEntry2 = new FlavorTextEntry();
        flavorTextEntry2.setFlavorText("wormadam description 2");

        flavorTextEntries.add(flavorTextEntry1);
        flavorTextEntries.add(flavorTextEntry2);

        pokemonInformation.setName("wormadam");
        pokemonInformation.setFlavorTextEntries(flavorTextEntries);
        pokemonInformation.setLegendary(true);

        var habitat = new Habitat();
        habitat.setName("habitat");
        pokemonInformation.setHabitat(habitat);

        var restTemplate = Mockito.mock(RestTemplate.class);

        when(restTemplate.getForEntity(ArgumentMatchers.anyString(), eq(PokemonInformation.class))).thenReturn(ResponseEntity.ok(pokemonInformation));

        var pokemonService = new PokemonService("https://pokeapi.co/api/v2", restTemplate);

        var result = pokemonService.getPokemonInformation("wormadam");
        assertEquals("wormadam", result.getName());
        assertEquals(2, result.getFlavorTextEntries().size());
        assertEquals("wormadam description 1", result.getFlavorTextEntries().get(0).getFlavorText());
        assertEquals("habitat", result.getHabitat().getName());
        assertEquals(true, result.isLegendary());

        verify(restTemplate).getForEntity(ArgumentMatchers.anyString(), eq(PokemonInformation.class));
    }

    @Test
    @DisplayName("when getPokemonInformation method is called with invalid name then response should be null")
    void test4() {

        var restTemplate = Mockito.mock(RestTemplate.class);

        when(restTemplate.getForEntity(ArgumentMatchers.anyString(), eq(PokemonInformation.class))).thenReturn(ResponseEntity.notFound().build());

        var pokemonService = new PokemonService("https://pokeapi.co/api/v2", restTemplate);

        assertThrows(ResourceNotFoundException.class, () -> pokemonService.getPokemonInformation(UUID.randomUUID().toString()));

        verify(restTemplate).getForEntity(ArgumentMatchers.anyString(), eq(PokemonInformation.class));
    }
}