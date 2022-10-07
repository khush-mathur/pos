package com.increff.pos.dto.helper;

import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.forms.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;

public class InventoryDtoHelper {
    public static InventoryData convertToData(InventoryPojo pojo) {
        InventoryData inventoryData = new InventoryData();
        inventoryData.setQuantity(pojo.getQuantity());
        return inventoryData;
    }

    public static InventoryPojo convertToPojo(InventoryData data) {
        InventoryPojo pojo = new InventoryPojo();
        pojo.setQuantity(data.getQuantity());
        return pojo;
    }

    public static InventoryPojo convertFormToPojo(InventoryForm form) {
        InventoryPojo pojo = new InventoryPojo();
        pojo.setQuantity(form.getQuantity());
        return pojo;
    }
}
