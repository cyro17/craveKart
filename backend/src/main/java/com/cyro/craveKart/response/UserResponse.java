package com.cyro.cravekart.response;

import com.cyro.cravekart.models.enums.USER_ROLE;
import com.cyro.cravekart.request.USER_STATUS;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserResponse {

  private String username;
  private String email;


//  private LocalDateTime createdAt;
//  private LocalDateTime updatedAt;
}
