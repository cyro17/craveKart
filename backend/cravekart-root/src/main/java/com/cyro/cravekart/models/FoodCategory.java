package com.cyro.cravekart.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"foods"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name"})})
public class FoodCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "restaurant_id")
  private Restaurant restaurant;

  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, orphanRemoval = true)
  @JsonIgnore
  private List<Food> foods = new ArrayList<>();

  @CreationTimestamp private LocalDateTime createdAt;
  @UpdateTimestamp private LocalDateTime updatedAt;
}
