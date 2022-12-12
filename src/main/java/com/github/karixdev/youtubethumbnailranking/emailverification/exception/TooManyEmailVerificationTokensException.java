package com.github.karixdev.youtubethumbnailranking.emailverification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TooManyEmailVerificationTokensException extends RuntimeException {
    public TooManyEmailVerificationTokensException() {
        super("You have requested too many email verification tokens");
    }
}
