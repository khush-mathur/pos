package org.example.service;

import org.example.dao.ProductDao;
import org.example.exception.ApiException;
import org.example.pojo.ProductPojo;
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
    public void add(ProductPojo productPojo) {
        if (validate(productPojo) && !productAlreadyExists(productPojo)) {
            productDao.add(productPojo);
            //remove
            inventoryService.addInInventory(productPojo.getId());
        }
    }

    @Transactional
    public void update(int id, ProductPojo inputProduct) {
        ProductPojo existingProduct = getProductById(id);
        if(validate(inputProduct) && !productAlreadyExists(inputProduct)){
            existingProduct.setMrp(inputProduct.getMrp());
            existingProduct.setBarcode(inputProduct.getBarcode());
            existingProduct.setName(inputProduct.getName());
            existingProduct.setBrandCategory(inputProduct.getBrandCategory());
            productDao.update(existingProduct);
        }
    }
    public ProductPojo getProductById(int id) {
        return productDao.get(id);
    }

    public List<ProductPojo> getAll(){
       List<ProductPojo> productList = productDao.getAll();
       return productList;
    }

    private boolean validate(ProductPojo product) {
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
        for(ProductPojo existingProduct: getAll()){
            if(existingProduct.getBarcode().equals(inputProduct.getBarcode()))
                throw new ApiException("Product with barcode : " +existingProduct.getBarcode() +" already exists");
        }
        return false;
    }

    public void delete(int id) {

    }
}
