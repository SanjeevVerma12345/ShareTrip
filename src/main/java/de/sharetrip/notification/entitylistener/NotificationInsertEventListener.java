package de.sharetrip.notification.entitylistener;

import de.sharetrip.core.entitylistener.BaseInsertEventListener;
import de.sharetrip.notification.domain.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationInsertEventListener extends BaseInsertEventListener<Notification> {

    @Override
    public Class<Notification> getEntityClass() {
        return Notification.class;
    }

}
