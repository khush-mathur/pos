package com.increff.pos.dto;

import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.forms.ProductForm;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import com.increff.pos.dto.helper.ProductDtoHelper;
import com.increff.pos.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ProductDto {

    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;

    public void add(List<ProductForm> productList) throws ApiException{
        for(ProductForm product:productList) {
            if(brandService.getBrandById(product.getBrandCategory())!=null) {
//                product = productDtoHelper.normalise(product);
                productService.add(ProductDtoHelper.convertToProductPojo(product));
            }
            else {
                throw new ApiException("Unable to add the product.");
            }
        }
    }
    public ProductData get(Integer id) {
        return ProductDtoHelper.convertToProductData(productService.getProductById(id));
    }

    public List<ProductData> getAll() {
        List<ProductData> productList = new ArrayList<>();
        for (ProductPojo productPojo : productService.getAll()){
            productList.add(ProductDtoHelper.convertToProductData(productPojo));
        }
        return productList;
    }

    public void update(Integer id, ProductForm productForm) throws ApiException{
        ProductPojo pojo = ProductDtoHelper.convertToProductPojo(productForm);
        if (brandService.getBrandById(productForm.getBrandCategory())!=null)
            productService.update(id,pojo);
    }

    public void delete(Integer id) {
        productService.delete(id);
    }
}
