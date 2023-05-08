package com.currencymarket.service.impl;

import com.currencymarket.dto.chatdto.ChatDto;
import com.currencymarket.dto.chatdto.MessageDto;
import com.currencymarket.entity.Chat;
import com.currencymarket.repository.ChatDao;
import com.currencymarket.repository.impl.ChatDaoImpl;
import com.currencymarket.service.ChatService;

import java.time.LocalDateTime;
import java.util.List;

public class ChatServiceImpl implements ChatService {
    private final ChatDao chatDao;

    public ChatServiceImpl() {
        chatDao = new ChatDaoImpl();
    }

    @Override
    public void sendMessage(MessageDto messageDto) {
        Chat message = new Chat(messageDto.getId(), messageDto.getMessage(), LocalDateTime.now());
        chatDao.save(message);
    }

    @Override
    public List<ChatDto> gelAllMessage() {
        return chatDao.getAll();
    }
}
