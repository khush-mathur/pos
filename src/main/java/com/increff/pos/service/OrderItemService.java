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
        return orderItemList;
    }

    @Transactional
    public void addOrderItem(OrderItemPojo pojo) throws ApiException {
        OrderItemPojo existingItem = orderItemDao.getByProductOrderId(pojo.getProductId(), pojo.getOrderId());
        InventoryPojo inventory = inventoryService.get(pojo.getProductId());
        Integer stockLeft = inventory.getQuantity();
        if (checkInventory(pojo, stockLeft)) {
            if (existingItem == null)
                orderItemDao.addOrderInCart(pojo);
            else
                existingItem.setQuantity(existingItem.getQuantity() + pojo.getQuantity());
            inventory.setQuantity(stockLeft- pojo.getQuantity());
        }
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderItemPojo updateOrderItem(Integer id, OrderItemPojo inputOrderItem) throws ApiException {
        OrderItemPojo existingItem = orderItemDao.getByProductOrderId(inputOrderItem.getProductId(),inputOrderItem.getOrderId());
        if(existingItem==null)
            throw new ApiException("No item exists with the product Id " + inputOrderItem.getProductId() + " in cart");
        InventoryPojo inventory = inventoryService.get(inputOrderItem.getProductId());
        Integer stockLeft = inventory.getQuantity()+ existingItem.getQuantity();
        if(checkInventory(inputOrderItem,stockLeft)){
            inventory.setQuantity(stockLeft-inputOrderItem.getQuantity());
            existingItem.setQuantity(inputOrderItem.getQuantity());
        }
        return existingItem;
    }

    @Transactional
    public List<OrderItemPojo> placeOrder(Integer orderId) throws ApiException{
    List<OrderItemPojo> orderItemList = orderItemDao.getByOrderId(orderId);
    if (orderItemList.size() == 0)
        throw new ApiException("No item in the cart to place order");
    return orderItemList;
    }

    private boolean checkInventory(OrderItemPojo orderItem,Integer stockLeft) throws ApiException{
        if( orderItem.getQuantity() > stockLeft)
            throw new ApiException("Not Enough stock. Only "+ stockLeft +" items left");
        return true;
    }
    public List<OrderItemPojo> getCartItem() throws ApiException{
        List<OrderItemPojo> orderItemList = orderItemDao.getByOrderId(-1);
        return orderItemList;
    }
    @Transactional
    public void delete(OrderItemPojo orderItem,InventoryPojo inventory) {
        inventory.setQuantity(inventory.getQuantity() + orderItem.getQuantity());
        orderItemDao.deleteById(orderItem.getId());
    }

    public OrderItemPojo getOrderItemById(Integer id) {
        return orderItemDao.getBrandDataById(id);
    }

    public void deleteByOrderId(Integer orderId) {
        orderItemDao.deleteByOrderId(orderId);
    }
}
