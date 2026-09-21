package com.notifysys.controller;

import com.notifysys.dto.NotificationRequest;
import com.notifysys.dto.NotificationResponse;
import com.notifysys.entity.Notification;
import com.notifysys.store.NotificationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
@Tag(name = "Notifications", description = "Create and fetch notifications")
public class NotificationController {
    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Operation(summary = "Create a notification",
            description = "Queues a new notification for the given user and channel. It is created in PENDING status.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Notification created",
                    content = @Content(schema = @Schema(implementation = NotificationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed: blank userId or message, or unknown channel",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<NotificationResponse> create(@Valid @RequestBody NotificationRequest request) {
        Notification notification =  Notification.newPending(request.getUserId(),request.getChannel(),request.getMessage());

        notificationRepository.save(notification);
        NotificationResponse response =  NotificationResponse.from(notification);
        return ResponseEntity.created(URI.create("/notifications/" + response.getId())).body(response);
    }

    @Operation(summary = "Get a notification by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification found",
                    content = @Content(schema = @Schema(implementation = NotificationResponse.class))),
            @ApiResponse(responseCode = "404", description = "No notification with that id", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getById(
            @Parameter(description = "Notification id", example = "20cb8787-1966-4746-b558-c16f97e46f26")
            @PathVariable UUID id) {

        Optional<Notification> result = notificationRepository.findById(id);

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Notification n = result.get();

        NotificationResponse response = new NotificationResponse(
                n.getId(),
                n.getUserId(),
                n.getChannel(),
                n.getMessage(),
                n.getCreatedAt(),
                n.getStatus()
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "List all notifications")
    @ApiResponse(responseCode = "200", description = "All notifications, oldest first")
    @GetMapping
    public List<NotificationResponse> getAll() {
        return notificationRepository.findAll().stream()
                .map(n -> new NotificationResponse(n.getId(), n.getUserId(), n.getChannel(), n.getMessage(), n.getCreatedAt(), n.getStatus()))
                .toList();
    }


}
