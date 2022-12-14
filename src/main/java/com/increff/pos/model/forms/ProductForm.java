package com.increff.pos.model.forms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter@NoArgsConstructor
public class ProductForm {
    private String barcode;
    private Integer brandCategory;
    private String name;
    private Double mrp;
}
