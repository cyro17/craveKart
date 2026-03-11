package com.cyro.cravekart.response;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.models.enums.USER_ROLE;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class UserResponse {

  private static final long serialVersionUID = 1L; // always good

  private String username;
  private String email;
  private List<USER_ROLE> roles;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static UserResponse from(User user) {
    return  new UserResponse(
        user.getUsername(),
        user.getEmail(),
        user.getRoles(),
        user.getCreatedAt(),
        user.getUpdatedAt()
    );
  }
}
