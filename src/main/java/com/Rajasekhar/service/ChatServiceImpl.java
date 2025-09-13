package com.Rajasekhar.service;

import com.Rajasekhar.model.Chat;
import com.Rajasekhar.repository.ChatRepository;
import org.springframework.stereotype.Service;


@Service
public class ChatServiceImpl implements ChatService {


    private ChatRepository chatRepository;
    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
