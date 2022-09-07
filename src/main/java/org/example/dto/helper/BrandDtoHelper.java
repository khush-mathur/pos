package org.example.dto.helper;

import org.example.model.data.BrandData;
import org.example.model.forms.BrandForm;
import org.example.pojo.BrandPojo;
import org.springframework.stereotype.Component;

@Component
public class BrandDtoHelper {
    public BrandPojo convertToBrandPojo(BrandForm form) {
        BrandPojo pojo = new BrandPojo();
        pojo.setCategory(form.getCategory());
        pojo.setBrandName(form.getBrandName());
        return pojo;
    }
    public BrandData convertToBrandData(BrandPojo pojo) {
        BrandData brandData = new BrandData();
        brandData.setCategory(pojo.getCategory());
        brandData.setBrandName(pojo.getBrandName());
        brandData.setId(pojo.getId());
        return brandData;
    }

}
