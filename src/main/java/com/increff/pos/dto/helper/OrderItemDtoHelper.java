package com.increff.pos.dto.helper;

import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.forms.OrderItemForm;
import com.increff.pos.pojo.OrderItemPojo;

public class OrderItemDtoHelper {

    public static OrderItemData convertToData(OrderItemPojo pojo) {
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setId(pojo.getId());
        orderItemData.setOrderId(pojo.getOrderId());
        orderItemData.setProductId(pojo.getProductId());
        orderItemData.setQuantity(pojo.getQuantity());
        orderItemData.setSellingPrice(pojo.getSellingPrice());
        return orderItemData;
    }

    public static OrderItemPojo convertToPojo(OrderItemForm orderItemForm) {
        OrderItemPojo pojo = new OrderItemPojo();
        pojo.setOrderId(orderItemForm.getOrderId());
        pojo.setProductId(orderItemForm.getProductId());
        pojo.setQuantity(orderItemForm.getQuantity());
        return pojo;
    }
}
