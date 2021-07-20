package com.truelayer.translation.apis.controllers;


import com.truelayer.translation.apis.models.PokemonInformationDto;
import com.truelayer.translation.services.IMapper;
import com.truelayer.translation.services.IPokemonService;
import com.truelayer.translation.services.ITranslationService;
import com.truelayer.translation.services.TranslationProvider;
import com.truelayer.translation.services.models.pokemon.PokemonInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
public class PokemonResource {

    private IPokemonService pokemonService;
    private ITranslationService translationService;

    private static final IMapper<PokemonInformation, PokemonInformationDto> mapper = (from) -> {
        PokemonInformationDto to = new PokemonInformationDto();
        to.setName(from.getName());

        var habitat = from.getHabitat();
        if (habitat != null) {
            to.setHabitat(from.getHabitat().getName());
        }

        to.setLegendary(from.isLegendary());

        var descriptions = from.getFlavorTextEntries();

        if (descriptions != null && !descriptions.isEmpty()) {
            to.setDescription(descriptions.get(0).getFlavorText());
        }

        return to;
    };

    @Autowired
    public PokemonResource(IPokemonService pokemonService, ITranslationService translationService) {
        this.pokemonService = pokemonService;
        this.translationService = translationService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<PokemonInformationDto> getPokemonInformation(@PathVariable("name") String name) {

        var pokemonInformation = pokemonService.getPokemonInformation(name);

        return new ResponseEntity<PokemonInformationDto>(mapper.map(pokemonInformation), HttpStatus.OK);

    }

    @GetMapping("/translated/{name}")
    public ResponseEntity<PokemonInformationDto> getTranslatedPokemonInformation(@PathVariable("name") String name) {

        var pokemonInformation = pokemonService.getPokemonInformation(name);

        var pokemonInformationDto = mapper.map(pokemonInformation);

        var translationProvider = getTranslationProvider(pokemonInformation);

        if (translationProvider != TranslationProvider.NONE) {

            var translatedInformation = translationService.translate(pokemonInformationDto.getDescription(), translationProvider);

            var content = translatedInformation.getContents();

            if (content != null) {
                pokemonInformationDto.setDescription(content.getTranslated());
            }

        }
        return new ResponseEntity<PokemonInformationDto>(pokemonInformationDto, HttpStatus.OK);
    }

    private static TranslationProvider getTranslationProvider(PokemonInformation pokemonInformation) {

        if (pokemonInformation == null) {
            return TranslationProvider.NONE;
        }

        if (pokemonInformation.getFlavorTextEntries() == null || pokemonInformation.getFlavorTextEntries().isEmpty()) {
            return TranslationProvider.NONE;
        }

        var habitat = pokemonInformation.getHabitat();

        if (habitat != null && "cave".equalsIgnoreCase(habitat.getName())) {
            return TranslationProvider.YODA;
        }

        if (pokemonInformation.isLegendary()) {
            return TranslationProvider.YODA;
        }

        return TranslationProvider.SHAKESPEARE;

    }
}