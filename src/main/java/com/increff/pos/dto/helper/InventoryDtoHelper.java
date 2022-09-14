package com.increff.pos.dto.helper;

import com.increff.pos.model.data.InventoryData;
import com.increff.pos.pojo.InventoryPojo;

public class InventoryDtoHelper {
    public static InventoryData convertToData(InventoryPojo pojo) {
        InventoryData inventoryData = new InventoryData();
        inventoryData.setProductId(pojo.getProductId());
        inventoryData.setQuantity(pojo.getQuantity());
        return inventoryData;
    }

    public static InventoryPojo convertToPojo(InventoryData data) {
        InventoryPojo pojo = new InventoryPojo();
        pojo.setQuantity(data.getQuantity());
        pojo.setProductId(data.getProductId());
        return pojo;
    }
}
