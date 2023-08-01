package com.vc.chat.service;

import com.vc.chat.domain.Chat;
import com.vc.chat.domain.Message;
import com.vc.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{

    private ChatRepository repository;

    @Autowired
    public ChatServiceImpl(ChatRepository repository) {
        this.repository = repository;
    }

    @Override
    public Chat startChat(Chat chat) {
        boolean isChatPresent = repository.findById(chat.getChat_id()).isPresent();
        if(isChatPresent){
          return repository.findById(chat.getChat_id()).get();
        }else
            return repository.save(chat);
    }

    @Override
    public Chat newMessage(String chat_id, Message message) {
        Chat chat = repository.findById(chat_id).get();

        if(chat.getList() == null) {
            chat.setList(Arrays.asList(message));
        }else{
            List<Message> messages = chat.getList();
            messages.add(message);
            chat.setList(messages);
        }
        return repository.save(chat);
    }

    @Override
    public List<Message> getMessages(String chat_id) {
        return repository.findById(chat_id).get().getList();
    }

    @Override
    public List<Chat> getAllChats(String email) {
        if(email.equals("care.fokus@gmail.com")){
            return repository.findAll();
        }else
        return null;
    }
}
