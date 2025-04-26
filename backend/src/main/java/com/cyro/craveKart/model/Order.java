package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Document(collection = "orders") // Specifies the MongoDB collection name
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

	@Id // MongoDB uses @Id for primary key
	private ObjectId id; // MongoDB typically uses String for the ID (you can use Long if preferred)

	@DBRef // MongoDB reference for User (similar to @ManyToOne in JPA)
	private User customer;

	@DBRef // MongoDB reference for Restaurant (similar to @ManyToOne in JPA)
	private Restaurant restaurant;

	private Long totalAmount;

	private String orderStatus;

	private Date createdAt; // MongoDB stores Date objects as is

	@DBRef // MongoDB reference for Address (similar to @ManyToOne in JPA)
	private Address deliveryAddress;

	@DBRef // MongoDB reference for OrderItem (similar to @OneToMany in JPA)
	private List<OrderItem> items;

	@DBRef // MongoDB reference for Payment (similar to @OneToOne in JPA)
	private Payment payment;

	private int totalItem;

	private int totalPrice;
}
