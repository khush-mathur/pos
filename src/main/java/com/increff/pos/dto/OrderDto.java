package com.increff.pos.dto;

import com.increff.pos.dto.helper.OrderDtoHelper;
import com.increff.pos.dto.helper.OrderItemDtoHelper;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
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

    public OrderData getOrder(Integer id) throws ApiException{
        return OrderDtoHelper.convertToData(orderService.getById(id));
    }

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

    public void placeOrder(Integer orderId) throws ApiException{
        orderItemService.placeOrder(orderId);
        orderService.updateOrderStatus(orderId);
    }

    public List<OrderItemData> getItemListByOrderId(Integer id) throws ApiException {
        List<OrderItemData> orderItemList = new ArrayList<>();
        for(OrderItemPojo pojo: orderItemService.getByOrderId(id)) {
            OrderItemData orderItem = OrderItemDtoHelper.convertToData(pojo);
            ProductPojo product = productService.getProductById(orderItem.getProductId());
            orderItem.setBarcode(product.getBarcode());
            orderItem.setProductName(product.getName());
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }
    public List<OrderData> fetchAll() {
        List<OrderData> orderList = new ArrayList<>();
        for (OrderPojo pojo : orderService.getAllOrders()) {
            orderList.add(OrderDtoHelper.convertToData(pojo));
        }
        return orderList;
    }

    public void generateInvoice(int orderId) throws ApiException {
        List<OrderItemData> orderItemList = new ArrayList<>();
        for (OrderItemPojo pojo : orderItemService.getByOrderId(orderId)) {
            OrderItemData orderItemData= OrderItemDtoHelper.convertToData(pojo);
            orderItemData.setProductName(productService.getProductById(pojo.getProductId()).getName());
            orderItemList.add(orderItemData);
        }
        orderService.getOrderInvoice(orderId, orderItemList);
    }

    public void deleteOrder(Integer id) {
        orderItemService.deleteByOrderId(id);
        orderService.deleteOrder(id);
    }
}
