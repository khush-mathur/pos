package org.example.model.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.forms.ProductForm;

@Getter
@Setter
@NoArgsConstructor
public class ProductData extends ProductForm {
    private int id;
}
