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

    public void update(Integer productId, Integer quantity) throws ApiException {
        if(validateInput(productId,quantity))
            inventoryService.updateQuantity(productId,quantity);
    }

    public List<InventoryPojo> fetchAll() {
        return inventoryService.getAll();
    }


    public void add(InventoryData inventoryData) throws ApiException {
        if(validateInput(inventoryData.getProductId(),inventoryData.getQuantity())
                && productService.getProductById(inventoryData.getProductId())==null)
            throw new ApiException("No product exist with id : "+ inventoryData.getProductId()+ " in master data");
        inventoryService.addInInventory(InventoryDtoHelper.convertToPojo(inventoryData));
    }
    private boolean validateInput(Integer productId,Integer quantity) throws ApiException {
        if(productId==null)
            throw new ApiException("Please enter valid Product Id");
        if(quantity==null || quantity < 0)
            throw new ApiException("Please enter valid Quantity");
        return true;
    }
}
