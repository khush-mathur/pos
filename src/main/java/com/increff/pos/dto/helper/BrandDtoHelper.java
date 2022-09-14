package com.increff.pos.dto.helper;

import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.forms.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import org.springframework.stereotype.Component;

@Component
public class BrandDtoHelper {
    public static BrandPojo convertToBrandPojo(BrandForm form) {
        BrandPojo pojo = new BrandPojo();
        pojo.setCategory(form.getCategory());
        pojo.setBrandName(form.getBrandName());
        return pojo;
    }
    public static BrandData convertToBrandData(BrandPojo pojo) {
        BrandData brandData = new BrandData();
        brandData.setCategory(pojo.getCategory());
        brandData.setBrandName(pojo.getBrandName());
        brandData.setId(pojo.getId());
        return brandData;
    }

}
