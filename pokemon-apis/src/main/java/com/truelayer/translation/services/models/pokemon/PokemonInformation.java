package com.truelayer.translation.services.models.pokemon;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class PokemonInformation {

    private String name;
    private Habitat habitat;
    private boolean isLegendary;
    public List<FlavorTextEntry> flavorTextEntries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    @JsonSetter("is_legendary")
    public void setLegendary(boolean legendary) {
        isLegendary = legendary;
    }

    public List<FlavorTextEntry> getFlavorTextEntries() {
        return flavorTextEntries;
    }

    @JsonSetter("flavor_text_entries")
    public void setFlavorTextEntries(List<FlavorTextEntry> flavorTextEntries) {
        this.flavorTextEntries = flavorTextEntries;
    }
}