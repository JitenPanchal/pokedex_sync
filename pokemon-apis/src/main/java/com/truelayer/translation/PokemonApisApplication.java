package com.truelayer.translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PokemonApisApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(PokemonApisApplication.class, args);
    }

//    @Bean
//    public IPokemonService getPokemonService() {
//        return new PokemonService(environment.getProperty("pokemon.api.url"), restTemplate());
//    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
