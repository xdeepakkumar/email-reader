package com.emailreader.controller;

import com.emailreader.dto.EmailResponse;
import com.emailreader.service.GmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gmail")
public class GmailController {
    private final GmailService gmailService;

    public GmailController(GmailService gmailService) {
        this.gmailService = gmailService;
    }

    @GetMapping("/latest-email")
    public List<EmailResponse> getLatestEmail() {
        try {
            return gmailService.readAllUnreadEmails();
        } catch (Exception e) {
            return null;
        }
    }
}
