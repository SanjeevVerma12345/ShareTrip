package de.sharetrip.notification.dataprovider;

import de.sharetrip.notification.domain.Notification;
import de.sharetrip.notification.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class NotificationDataProvider {

    private final NotificationRepository notificationRepository;

    public Notification saveNotification(final Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllPendingNotifications() {
        return notificationRepository.findAllByIsSentFalseAndFailedFalse();
    }
}
