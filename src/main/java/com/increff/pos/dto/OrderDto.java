package com.increff.pos.dto;

import com.increff.pos.dto.helper.OrderDtoHelper;
import com.increff.pos.dto.helper.OrderItemDtoHelper;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDto {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ProductService productService;

//    public OrderData get(Integer id) throws ApiException{
//        return OrderDtoHelper.convertToData(orderService.getById(id));
//    }

    public List<OrderData> getAll(){
        List<OrderData> orderList = new ArrayList<>();
        for(OrderPojo pojo : orderService.getAllOrders()){
            orderList.add(OrderDtoHelper.convertToData(pojo));
        }
        return orderList;
    }

    public OrderData addNewOrder() throws ApiException {
        return OrderDtoHelper.convertToData(orderService.addOrder());
    }

    public List<OrderItemData> placeOrder() throws ApiException{
        List<OrderItemData> itemDataList = new ArrayList<>();
        OrderData newOrder = addNewOrder();
        List<OrderItemPojo> pojoList = orderItemService.placeOrder(newOrder.getId());
        for (OrderItemPojo pojo :pojoList){
            itemDataList.add(OrderItemDtoHelper.convertToData(pojo));
        }
        return itemDataList;
    }

    public List<OrderItemData> getByOrderId(Integer id) throws ApiException {
        List<OrderItemData> orderItemList = new ArrayList<>();
        for(OrderItemPojo pojo: orderItemService.getByOrderId(id)) {
            OrderItemData orderItem = OrderItemDtoHelper.convertToData(pojo);
            orderItem.setBarcode(productService.getProductById(orderItem.getProductId()).getBarcode());
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }
    public List<OrderData> fetchAll() {
        List<OrderData> orderList = new ArrayList<>();
        for (OrderPojo p : orderService.getAllOrders()) {
            orderList.add(OrderDtoHelper.convertToData(p));
        }
        return orderList;
    }

    public void generateInvoice(int orderId) throws ApiException {
        List<OrderItemData> orderItemList = new ArrayList<>();
        for(OrderItemPojo pojo :orderItemService.getByOrderId(orderId)){
            orderItemList.add(OrderItemDtoHelper.convertToData(pojo));
        }
        orderService.getOrderInvoice(orderId,orderItemList);

    }
}
