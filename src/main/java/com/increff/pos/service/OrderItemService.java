package com.increff.pos.service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderItemService  {

    @Autowired
    OrderItemDao orderItemDao;
    @Autowired
    InventoryService inventoryService;

    public List<OrderItemPojo> getByOrderId(Integer id) throws ApiException {
        List<OrderItemPojo> orderItemList = orderItemDao.getByOrderId(id);
        if(orderItemList.size()==0)
            throw new ApiException("No orderItem is there with OrderID: "+ id);
        return orderItemList;
    }

    @Transactional
    public void addOrderItem(OrderItemPojo pojo) throws ApiException{
        if (orderItemDao.getByProductOrderId(pojo.getProductId(),pojo.getOrderId())!=null)
            throw new ApiException("Product already exist in the cart,please update quantity with the same order item");
        pojo.setOrderId(-1);
        orderItemDao.addOrderInCart(pojo);

    }

    @Transactional
    public OrderItemPojo updateOrderItem(Integer id, OrderItemPojo inputOrderItem) throws ApiException {
        OrderItemPojo existingItem = orderItemDao.getByProductOrderId(inputOrderItem.getProductId(),-1);
        if(existingItem==null)
            throw new ApiException("No item exists with the product Id " + inputOrderItem.getProductId() + "in cart");
        existingItem.setQuantity(inputOrderItem.getQuantity());
        return existingItem;
    }

    @Transactional
    public List<OrderItemPojo> placeOrder(Integer orderId) throws ApiException{
    List<OrderItemPojo> orderItemList = orderItemDao.getByOrderId(-1);
    if (orderItemList.size() == 0)
        throw new ApiException("No item in the cart to place order");
    for(OrderItemPojo orderItem : orderItemList){
        InventoryPojo inventory  = inventoryService.get(orderItem.getProductId());
        Integer stockLeft = inventory.getQuantity();
        if(orderItem.getQuantity() > stockLeft)
            throw new ApiException("Out of stock. "+ stockLeft +" items left for Product id" + orderItem.getProductId());
        else {
            inventory.setQuantity(inventory.getQuantity()-stockLeft);
            orderItem.setOrderId(orderId);
        }
    }
    return orderItemList;
    }

    public List<OrderItemPojo> getCartItem() throws ApiException{
        List<OrderItemPojo> orderItemList = orderItemDao.getByOrderId(-1);
        return orderItemList;
    }
}
