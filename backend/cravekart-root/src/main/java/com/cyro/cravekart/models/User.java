package com.cyro.cravekart.models;

import com.cyro.cravekart.models.enums.USER_ROLE;
import com.cyro.cravekart.request.USER_STATUS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(
    name = "users",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email")
    })
@Builder
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String lastName;

  @NotBlank
  @Column(unique = true, nullable = false)
  private String username;

  @Email @NotBlank private String email;

  @JsonIgnore private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "roles")
  private List<USER_ROLE> roles = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private USER_STATUS status;

  @Embedded private ContactInfo contact;

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> list =
        roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).toList();
    return list;
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.status.equals(USER_STATUS.ACTIVE);
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.status != USER_STATUS.BLOCKED;
  }

  @Override
  public boolean isEnabled() {
    return status == USER_STATUS.ACTIVE;
  }
}
