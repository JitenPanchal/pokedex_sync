package com.truelayer.translation.services.models.pokemon;

import com.fasterxml.jackson.annotation.JsonSetter;

public class FlavorTextEntry {
    private String flavorText;
    private Language language;
    private Version version;

    public String getFlavorText() {
        return flavorText;
    }

    @JsonSetter("flavor_text")
    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
