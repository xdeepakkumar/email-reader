package com.emailreader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gmail")
@Data
public class GmailProperties {
    private String username;
    private String password;
}

