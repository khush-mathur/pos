package com.increff.pos.dto;

import com.increff.pos.dto.helper.ProductDtoHelper;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.forms.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
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
            ProductPojo pojo = ProductDtoHelper.convertToProductPojo(product);
            if(validateInput(pojo) && brandService.getBrandById(pojo.getBrandCategory())!=null) {
                    productService.add(normalise(pojo));
            }
            else {
                throw new ApiException("Unable to add the product.");
            }
        }
    }
    public ProductData get(Integer id) throws ApiException{
        ProductData productData = ProductDtoHelper.convertToProductData(productService.getProductById(id));
        return setBrandCategory(productData);
    }

    public List<ProductData> getAll() throws ApiException{
        List<ProductData> productList = new ArrayList<>();
        for (ProductPojo productPojo : productService.getAll()){
            productList.add(setBrandCategory(ProductDtoHelper.convertToProductData(productPojo)));
        }
        return productList;
    }

    public void update(Integer id, ProductForm productForm) throws ApiException{
        ProductPojo productPojo = ProductDtoHelper.convertToProductPojo(productForm);
        if (validateInput(productPojo) && brandService.getBrandById(productPojo.getBrandCategory())!=null)
            productService.update(id,normalise(productPojo));
    }

    public void delete(Integer id) {
        productService.delete(id);
    }

    private ProductPojo normalise(ProductPojo product){
        product.setBarcode(product.getBarcode().trim().toLowerCase());
        product.setName(product.getName().trim().toLowerCase());
        return product;
    }

    private boolean validateInput(ProductPojo pojo) throws ApiException {
        if(pojo.getBrandCategory()==null)
            throw new ApiException("Please enter Brand-Category Id");
        else if(pojo.getMrp()==null)
            throw new ApiException("Please enter Mrp for the Product");
        if(pojo.getName()==null)
            throw new ApiException("Please enter name of Product");
        else if(pojo.getBarcode()==null)
            throw new ApiException("Please enter Barcode");
        return true;
    }
    private ProductData setBrandCategory(ProductData productData) throws ApiException{
        BrandPojo brand = brandService.getBrandById(productData.getBrandCategory());
        productData.setBrandName(brand.getBrandName());
        productData.setCategory(brand.getCategory());
        return productData;
    }
}
