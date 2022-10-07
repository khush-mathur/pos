package com.increff.pos.dto;

import com.increff.pos.dto.helper.InventoryDtoHelper;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.forms.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InventoryDto {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;
    public InventoryData get(String barcode) throws ApiException {
        ProductPojo product = productService.getProductByBarcode(barcode);
        InventoryData inventoryData= InventoryDtoHelper.convertToData(inventoryService.get(product.getId()));
        inventoryData.setProductName(product.getName());
        inventoryData.setBarcode(product.getBarcode());
        return inventoryData;
    }

    public void update(String barcode, Integer quantity) throws ApiException {
        ProductPojo product = productService.getProductByBarcode(barcode);
        if(validateInput(barcode,quantity))
            inventoryService.updateQuantity(product.getId(),quantity);
    }

    public List<InventoryData> fetchAll() throws ApiException {
        List<InventoryData> dataList = new ArrayList<>();
        for(InventoryPojo pojo :inventoryService.getAll()){
            InventoryData inventoryData= InventoryDtoHelper.convertToData(pojo);
            ProductPojo product = productService.getProductById(pojo.getProductId());
            inventoryData.setBarcode(product.getBarcode());
            inventoryData.setProductName(product.getName());
            dataList.add(inventoryData);
        }
        return dataList;
    }
    public void add(InventoryForm inventoryForm) throws ApiException {
        ProductPojo product = productService.getProductByBarcode(inventoryForm.getBarcode());
        if(validateInput(inventoryForm.getBarcode(),inventoryForm.getQuantity())) {
            InventoryPojo pojo = InventoryDtoHelper.convertFormToPojo(inventoryForm);
            pojo.setProductId(product.getId());
            inventoryService.addInInventory(pojo);
        }
    }
    private boolean validateInput(String barcode,Integer quantity) throws ApiException {
        if(barcode==null || barcode.isEmpty())
            throw new ApiException("Please enter valid Barcode: barcode can't be empty");
        if(quantity==null || quantity < 0)
            throw new ApiException("Please enter valid Quantity");
        return true;
    }
}
