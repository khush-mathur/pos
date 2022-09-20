package com.increff.pos.service;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductDao productDao;

    @Autowired
    InventoryService inventoryService;

    @Transactional
    public void add(ProductPojo productPojo) throws ApiException{
        if (validate(productPojo) && !productAlreadyExists(productPojo)) {
            productDao.add(productPojo);
        }
    }

    @Transactional
    public void update(Integer id, ProductPojo inputProduct) throws ApiException{
        System.out.println(id);
        ProductPojo existingProduct = getProductById(id);
        if(validate(inputProduct) && existingProduct!=null){
            existingProduct.setMrp(inputProduct.getMrp());
            existingProduct.setName(inputProduct.getName());
//            productDao.update(existingProduct);
        }
        else
            throw new ApiException("Unable to update the product with id "+ id);
    }
    public ProductPojo getProductById(Integer id) {
        return productDao.getByID(id);
    }

    public ProductPojo getProductByBarcode(String barcode) {
        return productDao.getByBarcode(barcode);
    }

    public List<ProductPojo> getAll(){
       List<ProductPojo> productList = productDao.getAll();
       return productList;
    }

    private boolean validate(ProductPojo product) throws ApiException{
        if(product.getBarcode().isEmpty()){
            throw new ApiException("Invalid barcode name: "+ product.getBarcode());
        } else if (product.getMrp() <=0.0 ) {
            throw new ApiException("Invalid MRP input: "+ product.getMrp());
        } else if (product.getName().isEmpty()) {
            throw new ApiException("Invalid product name input : "+ product.getName());
        }
        return true;
    }
    private boolean productAlreadyExists(ProductPojo inputProduct) throws ApiException{
        if (productDao.getByBarcode(inputProduct.getBarcode())!=null)
                throw new ApiException("Product with barcode : " +inputProduct.getBarcode() +" already exists");
        return false;
    }

    public void delete(Integer id) {

    }
}
