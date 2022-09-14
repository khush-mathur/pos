package com.increff.pos.dto.helper;

import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.forms.ProductForm;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoHelper {
    public static ProductPojo convertToProductPojo(ProductForm product) {
        ProductPojo pojo = new ProductPojo();
        pojo.setBarcode(product.getBarcode());
        pojo.setName(product.getName());
        pojo.setBrandCategory(product.getBrandCategory());
        pojo.setMrp(product.getMrp());
        return pojo;
    }
    public static ProductForm convertToProductForm(ProductPojo pojo) {
        ProductForm form = new ProductData();
        form.setBarcode(pojo.getBarcode());
        form.setName(pojo.getName());
        form.setBrandCategory(pojo.getBrandCategory());
        form.setMrp(pojo.getMrp());
        return form;
    }
    public static ProductData convertToProductData(ProductPojo pojo) {
        ProductData form = new ProductData();
        form.setBarcode(pojo.getBarcode());
        form.setName(pojo.getName());
        form.setBrandCategory(pojo.getBrandCategory());
        form.setMrp(pojo.getMrp());
        form.setId(pojo.getId());
        return form;
    }
    public static ProductForm normalise(ProductForm product) {
        product.setBarcode(product.getBarcode().toLowerCase());
        return product;
    }
}
