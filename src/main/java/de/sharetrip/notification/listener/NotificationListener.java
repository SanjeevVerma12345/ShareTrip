package de.sharetrip.notification.listener;

import de.sharetrip.notification.dataprovider.NotificationDataProvider;
import de.sharetrip.notification.domain.Notification;
import de.sharetrip.notification.exception.NotificationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
@AllArgsConstructor
public class NotificationListener {

    private final NotificationDataProvider notificationDataProvider;

    private final Consumer<Notification> notificationConsumer = this::consumeNotification;

    @PostConstruct
    private void init() {
        final List<Notification> listOfPendingNotification = notificationDataProvider.getAllPendingNotifications();
        listOfPendingNotification.stream()
                                 .forEach(notificationConsumer::accept);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void notificationCreatedListener(final Notification notification) {
        log.info(String.format("Notification created [%s]", notification));
        notificationConsumer.accept(notification);
    }

    @Retryable(value = {NotificationException.class},
            maxAttempts = 5,
            backoff = @Backoff(60000))
    public void consumeNotification(final Notification notification) {
        log.info(String.format("Consuming notification [%s] to send.", notification));
    }

    @Recover
    @Transactional
    public void unSendAbleNotification(final NotificationException notificationException) {
        log.error(String.format("Notification [%s] could not be send", notificationException));

        final Notification notification = notificationException.getNotification();
        notification.setFailed(true);
        notificationDataProvider.saveNotification(notification);
    }

}
