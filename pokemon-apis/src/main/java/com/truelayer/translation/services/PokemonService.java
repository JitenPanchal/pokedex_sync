package com.truelayer.translation.services;

import com.truelayer.translation.services.exceptions.ResourceNotFoundException;
import com.truelayer.translation.services.models.pokemon.PokemonInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PokemonService implements IPokemonService {

    private RestTemplate restTemplate;
    private String baseUrl;

    @Autowired
    public PokemonService(@Value("${pokemon.api.url}") String baseUrl, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public PokemonInformation getPokemonInformation(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("invalid pokemon name");
        }

        try {

            var responseEntity = restTemplate.getForEntity(String.format("%s/pokemon-species/%s", baseUrl, name), PokemonInformation.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            }
            else {
                throw new ResourceNotFoundException("pokemon named " + name + " not found");
            }

        }
        catch (Exception e){
            throw new ResourceNotFoundException("pokemon named " + name + " not found");
        }
    }
}