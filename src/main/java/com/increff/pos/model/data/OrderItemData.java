package com.increff.pos.model.data;

import com.increff.pos.model.forms.OrderItemForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemData extends OrderItemForm {
    private Integer id;
    private String productName;
    private Double sellingPrice;
}
