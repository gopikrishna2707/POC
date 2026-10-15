package com.example.Customer_Service.Services;
import com.example.Customer_Service.Constants.ConstantsClass;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TriageService {

    private final ChatClient chatClient;

    public TriageService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String analyze(
            MultipartFile sonarFile,
            MultipartFile checkmarxFile
    ) {

        if (sonarFile.isEmpty() || checkmarxFile.isEmpty()) {
            throw new IllegalArgumentException("Files cannot be empty");
        }

        /*
         * edge scenario
         */
//        if (!"application/json".equals(sonarFile.getContentType()) ||
//                !"application/json".equals(checkmarxFile.getContentType())) {
//            throw new IllegalArgumentException("Only JSON files are supported");
//        }

        String prompt = ConstantsClass.SECURITY_TRIAGE_PROMPT;

        String response =  chatClient.prompt()
                .user(user -> user
                        .text(prompt +
                                "\nReturn ONLY valid JSON.")
                        .media(
                                MediaType.APPLICATION_JSON,
                                sonarFile.getResource())
                        .media(
                                MediaType.APPLICATION_JSON,
                                checkmarxFile.getResource()))
                .call()
                .content();

        if (response == null || response.isBlank()) {
            throw new RuntimeException(
                    "No response received from Gemini");
        }
        return response;
    }
}
