package com.example.Customer_Service.Controllers;

import com.example.Customer_Service.Dto.ChatDto;
import com.example.Customer_Service.Services.ChatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatBotController {

    private final ChatService chatService;

    public ChatBotController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/get")
    public String chatRequest(@RequestBody ChatDto chatRequest){
        try {
            return chatService.generateResponse(chatRequest.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
