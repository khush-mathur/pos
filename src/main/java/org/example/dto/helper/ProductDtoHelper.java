package org.example.dto.helper;

import org.example.model.data.ProductData;
import org.example.model.forms.ProductForm;
import org.example.pojo.ProductPojo;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoHelper {
    public ProductPojo convertToProductPojo(ProductForm product) {
        ProductPojo pojo = new ProductPojo();
        pojo.setBarcode(product.getBarcode());
        pojo.setName(product.getName());
        pojo.setBrandCategory(product.getBrandCategory());
        pojo.setMrp(product.getMrp());
        return pojo;
    }
    public ProductForm convertToProductForm(ProductPojo pojo) {
        ProductForm form = new ProductData();
        form.setBarcode(pojo.getBarcode());
        form.setName(pojo.getName());
        form.setBrandCategory(pojo.getBrandCategory());
        form.setMrp(pojo.getMrp());
        return form;
    }
    public ProductData convertToProductData(ProductPojo pojo) {
        ProductData form = new ProductData();
        form.setBarcode(pojo.getBarcode());
        form.setName(pojo.getName());
        form.setBrandCategory(pojo.getBrandCategory());
        form.setMrp(pojo.getMrp());
        form.setId(pojo.getId());
        return form;
    }


    public ProductForm normalise(ProductForm product) {
        product.setBarcode(product.getBarcode().toLowerCase());
        return product;
    }


}
