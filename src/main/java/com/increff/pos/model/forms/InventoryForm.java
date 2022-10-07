package com.increff.pos.model.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryForm {
    private Integer quantity;
    private String barcode;
}
