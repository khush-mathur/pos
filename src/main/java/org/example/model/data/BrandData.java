package org.example.model.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.forms.BrandForm;
import org.example.pojo.BrandPojo;

@Getter
@Setter
@NoArgsConstructor
public class BrandData extends BrandForm {
    private int id;
}
