package org.example.dto;

import org.example.dto.helper.ProductDtoHelper;
import org.example.exception.ApiException;
import org.example.model.data.ProductData;
import org.example.model.forms.ProductForm;
import org.example.pojo.ProductPojo;
import org.example.service.BrandService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ProductDto {

    @Autowired
    ProductService productService;
    @Autowired
    BrandService brandService;

    @Autowired
    ProductDtoHelper productDtoHelper;


    public void add(List<ProductForm> productList) {
        for(ProductForm product:productList) {
            if(brandService.getBrandById(product.getBrandCategory())!=null) {
//                product = productDtoHelper.normalise(product);
                productService.add(productDtoHelper.convertToProductPojo(product));
            }
            else {
                throw new ApiException("Unable to add the product.");
            }
        }
    }
    public ProductForm get(int id) {
        return productDtoHelper.convertToProductForm(productService.getProductById(id));
    }

    public List<ProductData> getAll() {
        List<ProductData> productList = new ArrayList<>();
        for (ProductPojo productPojo : productService.getAll()){
            productList.add(productDtoHelper.convertToProductData(productPojo));
        }
        return productList;
    }

    public void update(int id, ProductForm productForm) {
        ProductPojo pojo = productDtoHelper.convertToProductPojo(productForm);
        if (brandService.getBrandById(productForm.getBrandCategory())!=null)
            productService.update(id,pojo);
    }

    public void delete(int id) {
        productService.delete(id);
    }
}
