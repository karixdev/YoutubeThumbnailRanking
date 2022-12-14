package com.github.karixdev.ratingyoutubethumbnailsapi.youtube.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class YoutubeVideoNotFoundException extends RuntimeException {
    public YoutubeVideoNotFoundException() {
        super("Youtube video with provided id not found");
    }
}
