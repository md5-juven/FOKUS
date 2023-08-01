package com.vc.chat.service;

import com.vc.chat.domain.Chat;
import com.vc.chat.domain.Message;

import java.util.List;

public interface ChatService {

    Chat startChat(Chat chat);

    Chat newMessage(String chat_id, Message message);

    List<Message> getMessages(String chat_id);

    List<Chat> getAllChats(String email);
}
