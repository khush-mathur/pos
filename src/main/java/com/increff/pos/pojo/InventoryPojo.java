package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "Inventory")
public class InventoryPojo {
    @Id
    private Integer productId;
    @Column(nullable = false)
    private Integer quantity;
}
