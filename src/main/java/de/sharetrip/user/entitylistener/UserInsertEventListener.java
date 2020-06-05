package de.sharetrip.user.entitylistener;

import de.sharetrip.core.entitylistener.BaseInsertEventListener;
import de.sharetrip.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserInsertEventListener extends BaseInsertEventListener<User> {

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
