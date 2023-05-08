package com.currencymarket.service;

import com.currencymarket.dto.chatdto.ChatDto;
import com.currencymarket.dto.chatdto.MessageDto;

import java.util.List;

public interface ChatService {
    void sendMessage(MessageDto messageDto);
    List<ChatDto> gelAllMessage();
}
