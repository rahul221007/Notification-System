package com.notifysys.store;

import com.notifysys.entity.Notification;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;



@Component
public class InMemoryNotificationStore {

    private final Map<UUID, Notification> store = new ConcurrentHashMap<UUID, Notification>();

    public Notification save(Notification notification) {
        store.put(notification.getId(), notification);
        return notification;
    }
    public Optional<Notification> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public Collection<Notification> findAll() {
        return store.values();
    }
}
