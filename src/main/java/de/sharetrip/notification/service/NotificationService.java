package de.sharetrip.notification.service;

import de.sharetrip.notification.dataprovider.NotificationDataProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationDataProvider notificationDataProvider;
    //    public void test() throws FirebaseMessagingException {
    //        final Message message = Message.builder()
    //                                       .setToken("token")
    //                                       .setNotification(new Notification("notificationRequestDto.getTitle()",
    //                                               "notificationRequestDto.getBody()"))
    //                                       .putData("content", "notificationRequestDto.getTitle()")
    //                                       .putData("body", " notificationRequestDto.getBody()")
    //                                       .build();
    //        FirebaseMessaging.getInstance().send(message);
    //    }

}
