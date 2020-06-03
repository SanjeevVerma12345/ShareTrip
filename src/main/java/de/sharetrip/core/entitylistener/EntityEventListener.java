package de.sharetrip.core.entitylistener;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import org.hibernate.event.spi.EventType;

import java.util.Objects;

@Value
@Builder
@ToString
public class EntityEventListener<T> {

    private final EventType eventType;

    private final String entityName;

    private final T bean;

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }

        if (Objects.isNull(o) || getClass() != o.getClass()) {
            return false;
        }

        final EntityEventListener<T> that = (EntityEventListener<T>) o;

        return Objects.equals(eventType, that.eventType) &&
                Objects.equals(entityName, that.entityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventType, entityName, bean);
    }
}
