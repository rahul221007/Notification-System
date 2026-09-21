package com.notifysys.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="notifications")

public class Notification {
    @Id
    private UUID id;
    private String userId;
    @Enumerated(EnumType.STRING)
    private Channel channel;
    private String message;
    private Instant createdAt;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    public static Notification newPending(String userId,Channel channel,String message) {
        Notification notification = new Notification();
        notification.setId(UUID.randomUUID());
        notification.setUserId(userId);
        notification.setChannel(channel);
        notification.setMessage(message);
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(Instant.now());
        return notification;

    }

}
