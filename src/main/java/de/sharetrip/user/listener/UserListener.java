package de.sharetrip.user.listener;

import de.sharetrip.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@AllArgsConstructor
public class UserListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void userCreatedListener(final User user) {
        log.info(String.format("User %s is created", user));
    }
}
