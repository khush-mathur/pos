package org.example.service;

import org.example.dao.InventoryDao;
import org.example.exception.ApiException;
import org.example.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    InventoryDao inventoryDao;

    public InventoryPojo get(int id) throws ApiException{
        InventoryPojo pojo = inventoryDao.get(id);
        if(pojo == null)
            throw new ApiException("No product with id " + id + " present in inventory");
        return pojo;
    }

    @Transactional
    public void addInInventory(int productId) {
        InventoryPojo pojo = new InventoryPojo(productId,0);
        inventoryDao.add(pojo);
    }


    @Transactional
    public void increaseQuantity(int id, int quantity) {
        InventoryPojo existingPojo = get(id);
        existingPojo.setQuantity(existingPojo.getQuantity()+quantity);
        inventoryDao.update(existingPojo);
    }
    @Transactional
    public void decreaseQuantity(int id, int quantity) {
        InventoryPojo existingPojo = get(id);
        if (quantity>existingPojo.getQuantity()){
            throw new ApiException("Invalid quantity input");
        }
        existingPojo.setQuantity(existingPojo.getQuantity()-quantity);
        inventoryDao.update(existingPojo);
    }

    public List<InventoryPojo> getAll() {
        return inventoryDao.getAll();
    }
}
