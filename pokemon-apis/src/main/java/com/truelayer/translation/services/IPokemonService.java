package com.truelayer.translation.services;


import com.truelayer.translation.services.models.pokemon.PokemonInformation;

public interface IPokemonService {
    PokemonInformation getPokemonInformation(String name);
}
