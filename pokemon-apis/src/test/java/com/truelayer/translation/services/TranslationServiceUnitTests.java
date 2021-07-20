package com.truelayer.translation.services;

import com.truelayer.translation.services.models.translator.TranslatedInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class TranslationServiceUnitTests {

    private ITranslationService translationService;
    private String translatorApiUrl;

    @BeforeEach
    void beforeEach() {
        translatorApiUrl = "https://api.funtranslations.com/translate/%s.json?text=%s";
        translationService = new TranslationService(translatorApiUrl, new RestTemplate());
    }

    @Test
    @DisplayName("when translate method is called with null parameter then IllegalArgumentException should be thrown")
    void test1() {
        assertThrows(IllegalArgumentException.class, () -> translationService.translate(null, TranslationProvider.YODA));
    }

    @Test
    @DisplayName("when translate method is called with blank text parameter then IllegalArgumentException should be thrown")
    void test2() {
        assertThrows(IllegalArgumentException.class, () -> translationService.translate("", TranslationProvider.YODA));
    }

    @Test
    @DisplayName("when translate method is called with valid text then response text should be same as input text")
    void test4() {

        var success = new TranslatedInformation.Success();
        success.setTotal(1);

        var contents = new TranslatedInformation.Contents();
        contents.setTranslated("Translated text");
        contents.setText("text");
        contents.setTranslation("shakespeare");

        var translatedInformation = new TranslatedInformation(success, contents);

        var restTemplate = Mockito.mock(RestTemplate.class);

        when(restTemplate.getForEntity(ArgumentMatchers.anyString(), eq(TranslatedInformation.class))).thenReturn(ResponseEntity.ok(translatedInformation));

        translationService = new TranslationService(translatorApiUrl, restTemplate);

        var result = translationService.translate("text", TranslationProvider.SHAKESPEARE);
        assertEquals("Translated text", result.getContents().getTranslated());
        assertEquals("shakespeare", result.getContents().getTranslation());
        assertEquals("text", result.getContents().getText());
        assertEquals(1, result.getSuccess().getTotal());

        verify(restTemplate).getForEntity(ArgumentMatchers.anyString(), eq(TranslatedInformation.class));
    }
}