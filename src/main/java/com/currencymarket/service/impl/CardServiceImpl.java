package com.currencymarket.service.impl;

import com.currencymarket.dto.carddto.CardDto;
import com.currencymarket.repository.CardDao;
import com.currencymarket.repository.impl.CardDaoImpl;
import com.currencymarket.service.CardService;

import java.util.Objects;

public class CardServiceImpl implements CardService {
    private final CardDao cardDao;

    public CardServiceImpl() {
        cardDao = new CardDaoImpl();
    }

    @Override
    public void activateCard(CardDto cardDto) {
        cardDao.save(cardDto);
    }

    @Override
    public CardDto getCart(int userId) {
        return cardDao.getByUserId(userId);
    }

    @Override
    public float getCashFromCard(int userId) {
        CardDto card = cardDao.getByUserId(userId);
        if(Objects.equals(card, null)) {
            return 0f;
        }
        return card.getCash();
    }
}
