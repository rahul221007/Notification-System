package com.notifysys.dto;

import com.notifysys.entity.Channel;
import com.notifysys.entity.Notification;
import com.notifysys.entity.NotificationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Schema(description = "A stored notification")
public class NotificationResponse {
    @Schema(description = "Generated notification id", example = "20cb8787-1966-4746-b558-c16f97e46f26")
    private UUID id;
    @Schema(description = "Id of the user to notify", example = "user-1")
    private String userId;
    @Schema(description = "Delivery channel", example = "EMAIL")
    private Channel channel;
    @Schema(description = "Message body", example = "Your order has shipped")
    private String message;
    @Schema(description = "Creation time, UTC", example = "2026-09-21T16:56:18.659945Z")
    private Instant createdAt;
    @Schema(description = "Delivery status. New notifications start as PENDING", example = "PENDING")
    private NotificationStatus status;
    public static NotificationResponse from(Notification n) {
        return new NotificationResponse(
                n.getId(), n.getUserId(), n.getChannel(), n.getMessage(), n.getCreatedAt(), n.getStatus()
        );
    }
}
