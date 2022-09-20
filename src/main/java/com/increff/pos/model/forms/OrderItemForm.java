package com.increff.pos.model.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemForm {
    private String barcode;
    private Integer quantity;
    private Integer orderId;
    private Integer productId;
}
