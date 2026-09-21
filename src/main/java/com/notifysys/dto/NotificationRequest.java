package com.notifysys.dto;

import com.notifysys.entity.Channel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Payload for creating a notification")
public class NotificationRequest {
    @Schema(description = "Id of the user to notify", example = "user-1")
    @NotBlank
    private String userId;

    @Schema(description = "Delivery channel", example = "EMAIL")
    @NotNull
    private Channel channel;

    @Schema(description = "Message body to deliver", example = "Your order has shipped")
    @NotBlank
    private String message;



}
