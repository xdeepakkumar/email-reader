package com.emailreader.dto;

import lombok.Data;

@Data
public class EmailResponse {
    private String subject;
    private String body;
}
