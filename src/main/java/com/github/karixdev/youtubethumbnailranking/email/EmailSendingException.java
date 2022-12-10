package com.github.karixdev.youtubethumbnailranking.email;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailSendingException extends RuntimeException {
    public EmailSendingException() {
        super("Error occurred while sending email");
    }
}
