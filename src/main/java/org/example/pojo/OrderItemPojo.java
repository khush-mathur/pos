package org.example.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "OrderItem")
public class OrderItemPojo {
    @Id
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double sellingPrice;
}
