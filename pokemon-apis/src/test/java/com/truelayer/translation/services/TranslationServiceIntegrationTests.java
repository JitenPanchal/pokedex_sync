package com.truelayer.translation.services;

import com.truelayer.translation.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

public class TranslationServiceIntegrationTests {

    private String translatorApiUrl = "https://api.funtranslations.com/translate/%s.json?text=%s";

    private RestTemplate restTemplate;

    private ITranslationService translationService;

    @BeforeEach
    void beforeEach() {

        restTemplate = new RestTemplate();
        translationService = new TranslationService(translatorApiUrl, restTemplate);
    }

    @Test
    @DisplayName("when translate method is called with valid text e.g. hi then response text should be same as input text")
    void test1() throws Exception {
        var expectedName = "hi";
        var result = translationService.translate("hi",TranslationProvider.YODA);
        assertEquals(expectedName, result.getContents().getText());
    }

    @Test
    @DisplayName("when translate method is called with valid number e.g. 3 then response text & translated text should be same as input text")
    void test2() throws Exception {
        var expectedName = "3";
        var result = translationService.translate("3",TranslationProvider.YODA);
        assertEquals(expectedName, result.getContents().getText());
        assertEquals(expectedName, result.getContents().getTranslated());
    }
}
