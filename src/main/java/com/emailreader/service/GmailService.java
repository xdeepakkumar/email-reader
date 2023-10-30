package com.emailreader.service;

import com.emailreader.config.GmailProperties;
import com.emailreader.dto.EmailResponse;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.search.FlagTerm;
import jakarta.mail.search.SearchTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GmailService {
    @Autowired
    private GmailProperties gmailProperties;

    public List<EmailResponse> readAllUnreadEmails() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(properties, null);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", gmailProperties.getUsername(), gmailProperties.getPassword());

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        // Create a search term to filter unread messages
        SearchTerm searchTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);

        Message[] unreadMessages = inbox.search(searchTerm);

        if (unreadMessages.length == 0) {
            return Collections.emptyList();
        }

        List<EmailResponse> emailList = new ArrayList<>();

        for (Message message : unreadMessages) {
            // Extract email subject
            String subject = message.getSubject();

            // Extract email body
            String body = extractTextFromMessage(message);

            if (subject != null && body != null) {
                EmailResponse response = new EmailResponse();
                response.setSubject(subject);
                response.setBody(body.substring(0, body.length() - 2));
                emailList.add(response);
            }
        }

        return emailList;
    }

    private String extractTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) message.getContent();
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    text.append(bodyPart.getContent().toString());
                }
            }
            return text.toString();
        }
        return null;
    }
}
