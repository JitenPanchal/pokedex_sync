package com.truelayer.translation.services;

import com.truelayer.translation.services.models.translator.TranslatedInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class TranslationService implements ITranslationService {

    private RestTemplate restTemplate;
    private String translatorApiUrl;

    @Autowired
    public TranslationService(@Value("${translator.api.url}") String translatorApiUrl, RestTemplate restTemplate) {
        this.translatorApiUrl = translatorApiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public TranslatedInformation translate(String text, TranslationProvider translationProvider) {

        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("text can not be null or blank");
        }

        try {

            var responseEntity = restTemplate.getForEntity(String.format(translatorApiUrl, translationProvider.name().toLowerCase(), text), TranslatedInformation.class);
            return responseEntity.getBody();

        } catch (HttpClientErrorException e) {

            var success = new TranslatedInformation.Success();
            success.setTotal(1);

            var contents = new TranslatedInformation.Contents();
            contents.setTranslated(text);
            contents.setText(text);
            contents.setTranslation(translationProvider.name().toLowerCase());

            return new TranslatedInformation(success, contents);
        }

    }
}
