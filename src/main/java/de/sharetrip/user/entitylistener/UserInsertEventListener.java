package de.sharetrip.user.entitylistener;

import de.sharetrip.core.entitylistener.BaseInsertEventListener;
import de.sharetrip.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserInsertEventListener extends BaseInsertEventListener<User> {

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
