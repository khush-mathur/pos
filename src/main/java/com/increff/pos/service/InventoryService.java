package com.increff.pos.service;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    InventoryDao inventoryDao;

    public InventoryPojo get(Integer productId) throws ApiException{
        InventoryPojo pojo = inventoryDao.get(productId);
        if(pojo == null)
            throw new ApiException("No product with id " + productId + " present in inventory");
        return pojo;
    }

    @Transactional
    public void addInInventory(InventoryPojo pojo) throws ApiException{
        if (inventoryDao.get(pojo.getProductId())!=null)
            throw new ApiException("Product already Exists in Inventory");
        inventoryDao.add(pojo);
    }


    @Transactional
    public void updateQuantity(Integer id, Integer quantity) throws ApiException{
        InventoryPojo existingPojo = get(id);
        if(quantity<=0)
            throw new ApiException("Invalid input: quantity cannot be negative");
        existingPojo.setQuantity(quantity);
//        inventoryDao.update(existingPojo);
    }
    @Transactional
    public void decreaseQuantity(Integer id, Integer quantity) throws ApiException {
        InventoryPojo existingPojo = get(id);
        if (quantity>existingPojo.getQuantity()){
            throw new ApiException("Invalid quantity input");
        }
        existingPojo.setQuantity(existingPojo.getQuantity()-quantity);
//        inventoryDao.update(existingPojo);
    }

    public List<InventoryPojo> getAll() {
        return inventoryDao.getAll();
    }
}
