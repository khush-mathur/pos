package com.increff.pos.dto;

import com.increff.pos.dto.helper.InventoryDtoHelper;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryDto {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;
    public InventoryPojo get(Integer id) throws ApiException {
         return inventoryService.get(id);
    }

    public void update(Integer id, Integer quantity) throws ApiException {
         inventoryService.updateQuantity(id,quantity);
    }

    public List<InventoryPojo> fetchAll() {
        return inventoryService.getAll();
    }


    public void add(InventoryData inventoryData) throws ApiException {
        if(productService.getProductById(inventoryData.getProductId())==null)
            throw new ApiException("No product exist with id : "+ inventoryData.getProductId()+ " in master data");
        inventoryService.addInInventory(InventoryDtoHelper.convertToPojo(inventoryData));
    }
}
