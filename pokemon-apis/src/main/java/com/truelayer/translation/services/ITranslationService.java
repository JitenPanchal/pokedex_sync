package com.truelayer.translation.services;

import com.truelayer.translation.services.models.translator.TranslatedInformation;

public interface ITranslationService {

    TranslatedInformation translate(String text, TranslationProvider translationProvider);

}
