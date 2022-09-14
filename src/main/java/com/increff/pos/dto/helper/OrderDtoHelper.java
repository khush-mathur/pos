package com.increff.pos.dto.helper;

import com.increff.pos.model.data.OrderData;
import com.increff.pos.pojo.OrderPojo;

import java.time.format.DateTimeFormatter;

public class OrderDtoHelper {
    public static OrderData convertToData(OrderPojo pojo) {
        OrderData orderData = new OrderData();
        orderData.setId(pojo.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z");
        String dateTime = pojo.getDateTime().format(formatter);
        orderData.setDateTime(dateTime);
        return orderData;
    }
}
