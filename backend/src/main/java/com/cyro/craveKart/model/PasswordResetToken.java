package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Document(collection = "passwordResetTokens") // Specifies the MongoDB collection name
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

	@Id // MongoDB uses @Id for primary key
	private ObjectId id; // MongoDB typically uses String for the ID (you can use Integer if preferred)

	private @NonNull String token;

	@DBRef // MongoDB reference for User (similar to @ManyToOne in JPA)
	private @NonNull User user;

	private @NonNull Date expiryDate;

	public boolean isExpired() {
		return expiryDate.before(new Date());
	}
}
