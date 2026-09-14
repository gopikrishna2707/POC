package com.example.Customer_Service.Services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;
import org.springframework.ai.openai.OpenAiChatOptions;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String generateResponse(String prompt){
        return chatClient.prompt()
                .user(prompt).
                options(ChatOptions.builder()
                        .maxTokens(30).build())
                .call()
                .content();
    }
}
