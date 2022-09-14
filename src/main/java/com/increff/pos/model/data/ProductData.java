package com.increff.pos.model.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.increff.pos.model.forms.ProductForm;

@Getter
@Setter
@NoArgsConstructor
public class ProductData extends ProductForm {
    private int id;
}
