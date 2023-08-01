package com.vc.chat.controller;

import com.vc.chat.domain.Chat;
import com.vc.chat.domain.Message;
import com.vc.chat.service.ChatService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/support")
public class ChatController {

    private ChatService service;

    @Autowired
    public ChatController(ChatService service) {
        this.service = service;
    }

    @PostMapping("/newChat")
    public ResponseEntity<?> startChat(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String chatId = "support"+claims.getSubject();
        Chat chat = new Chat();
        chat.setChat_id(chatId);
        return new ResponseEntity<>(service.startChat(chat), HttpStatus.OK);
    }

    @PostMapping("/newMessage/{chat_id}")
    public ResponseEntity<?> newMessage(@PathVariable String chat_id, @RequestBody Message message,HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        message.setEmail(email);
        return new ResponseEntity<>(service.newMessage(chat_id,message),HttpStatus.OK);
    }

    @GetMapping("/get-messages/{chat_id}")
    public ResponseEntity<?> getMessages(@PathVariable String chat_id){
        return new ResponseEntity<>(service.getMessages(chat_id),HttpStatus.OK);
    }

    @GetMapping("get-chats")
    public ResponseEntity<?> getChats(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        return new ResponseEntity<>(service.getAllChats(email),HttpStatus.OK);
    }
}
