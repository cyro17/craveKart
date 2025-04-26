package com.cyro.craveKart.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "coupons") // Specifies the MongoDB collection name
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

    @Id // MongoDB uses @Id for primary key
    private ObjectId id; // MongoDB typically uses String for the ID (you can use Long if needed)

    private String code;
    private double discountAmount;

    private Date validityPeriod; // MongoDB stores Date objects as is

    private String termsAndConditions;
}
