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

@Document(collection = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

	@Id
	private ObjectId id;

	@DBRef
	private User customer;

	@DBRef
	private Restaurant restaurant;

	private Double totalAmount;

	private String orderStatus;

	private Date createdAt;

	@DBRef
	private Address deliveryAddress;

	@DBRef
	private List<OrderItem> items;

	@DBRef
	private Payment payment;

	private int totalItem;

	private int totalPrice;
}
