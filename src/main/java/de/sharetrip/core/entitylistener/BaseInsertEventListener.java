package de.sharetrip.core.entitylistener;

import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;

import java.util.Optional;

public abstract class BaseInsertEventListener<T>
        extends BaseEntityListener
        implements PostInsertEventListener {

    @Override
    public boolean requiresPostCommitHanding(final EntityPersister persister) {
        return false;
    }

    @Override
    public void onPostInsert(final PostInsertEvent event) {
        Optional.of(event)
                .map(PostInsertEvent::getEntity)
                .filter(getEntityClass()::isInstance)
                .map(getEntityClass()::cast)
                .ifPresent(applicationEventPublisher::publishEvent);
    }

    public abstract Class<T> getEntityClass();

}
