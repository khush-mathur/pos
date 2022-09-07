package org.example.dto;

import org.example.model.data.BrandData;
import org.example.model.data.InventoryData;
import org.example.pojo.InventoryPojo;
import org.example.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryDto {

    @Autowired
    InventoryService inventoryService;
    public InventoryPojo get(int id) {
         return inventoryService.get(id);
    }

    public void add(int id, int quantity) {
         inventoryService.increaseQuantity(id,quantity);
    }

    public void remove(int id, int quantity) {
         inventoryService.decreaseQuantity(id,quantity);
    }

    public List<InventoryPojo> fetchAll() {
        return inventoryService.getAll();
    }
}
