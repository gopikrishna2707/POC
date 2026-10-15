package com.example.Customer_Service.Controllers;
import com.example.Customer_Service.Services.TriageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class TriageController {

    private final TriageService triageService;

    @PostMapping(
            value = "/triage",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String triage(
            @RequestParam("sonarReport") MultipartFile sonarReport,
            @RequestParam("checkmarxReport") MultipartFile checkmarxReport
    ) throws Exception {
        return triageService.analyze(
                sonarReport,
                checkmarxReport
        );
    }
}



