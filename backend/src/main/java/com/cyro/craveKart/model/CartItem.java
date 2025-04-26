package com.cyro.craveKart.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cart_items")
public class CartItem {
    @Id
    private ObjectId id;  // MongoDB automatically generates this field, no need for @GeneratedValue

    @JsonIgnore
    @DBRef  // MongoDB uses DBRef to reference other documents (Cart and Food in this case)
    private Cart cart;

    @DBRef  // MongoDB uses DBRef to reference other documents (Cart and Food in this case)
    private Food food;

    private int quantity;
    private List<String> ingredients;
    private Long totalPrice;
}
