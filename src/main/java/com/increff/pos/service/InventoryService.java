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

    public InventoryPojo get(Integer id) throws ApiException{
        InventoryPojo pojo = inventoryDao.getByProductId(id);
        if(pojo == null)
            throw new ApiException("No such product present in inventory");
        return pojo;
    }

    @Transactional
    public void addInInventory(InventoryPojo pojo) {
        InventoryPojo inventory = inventoryDao.getByProductId(pojo.getProductId());
        if (inventoryDao.getByProductId(pojo.getProductId())==null)
           inventoryDao.add(pojo);
        else
            inventory.setQuantity(pojo.getQuantity());
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
