package com.emailreader.schedule;

import com.emailreader.service.GmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class EmailReaderScheduler {

    @Autowired
    private GmailService gmailService;

    @Scheduled(fixedDelay = 1000)
    public void gmailScheduler() throws Exception {
        log.info("Reading new mail start at : {}", LocalDateTime.now());
        gmailService.readAllUnreadEmails();
        log.info("Reading new mail end at : {}", LocalDateTime.now());
    }
}
