package com.currencymarket.service;

import com.currencymarket.dto.carddto.CardDto;

public interface CardService {
    void activateCard(CardDto cardDto);

    CardDto getCart(int userId);

    float getCashFromCard(int userId);
}
