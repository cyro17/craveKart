package com.cravekart.core.events.notification;


import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserSignupEvent extends BaseNotificationEvent{

    private String firstName;
    private String lastName;
    private String username;
    private String role;


}
