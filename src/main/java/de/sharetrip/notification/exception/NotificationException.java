package de.sharetrip.notification.exception;

import de.sharetrip.notification.domain.Notification;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotificationException extends Exception {

    @Getter
    private final Notification notification;

    public NotificationException(final String message,
                                 final Notification notification) {
        super(message);
        this.notification = notification;
    }
}
