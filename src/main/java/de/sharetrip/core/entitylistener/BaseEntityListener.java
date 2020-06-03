package de.sharetrip.core.entitylistener;

import com.google.api.client.util.Lists;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Configuration
public class BaseEntityListener {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    protected ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ApplicationContext applicationContext;

    private EventListenerRegistry eventListenerRegistry;

    private static final List<EntityEventListener> listOfEntityEventListeners = Lists.newArrayList();

    private final Consumer<EntityEventListener> entityEventListenerConsumer = this::consumeEntityEventListener;

    @PostConstruct
    private void init() {
        final SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        eventListenerRegistry = sessionFactory.getServiceRegistry()
                                              .getService(EventListenerRegistry.class);
        addPostInsertEventListener();
    }

    private void addPostInsertEventListener() {
        final Map<String, BaseInsertEventListener> beans = applicationContext.getBeansOfType(BaseInsertEventListener.class);

        beans.entrySet()
             .stream()
             .forEach(bean ->
                     entityEventListenerConsumer.accept(
                             EntityEventListener
                                     .builder()
                                     .entityName(bean.getKey())
                                     .eventType(EventType.POST_INSERT)
                                     .bean(bean.getValue())
                                     .build()));
    }

    private void consumeEntityEventListener(final EntityEventListener entity) {

        listOfEntityEventListeners
                .stream()
                .filter(entity::equals)
                .findAny()
                .ifPresentOrElse(ignore ->
                                log.info(String.format("Listener [%s] is already registered", entity)),
                        () -> {
                            listOfEntityEventListeners.add(entity);
                            eventListenerRegistry.appendListeners(
                                    entity.getEventType(),
                                    entity.getBean());
                            log.info(String.format("Registering listener [%s]", entity));
                        });


    }
}
