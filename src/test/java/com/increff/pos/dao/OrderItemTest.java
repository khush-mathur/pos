package com.increff.pos.dao;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.pojo.OrderItemPojo;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class OrderItemTest extends AbstractUnitTest {
    
    @Resource
    OrderItemDao dao;

    @Test
    public void daoAdd(){
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(new Random().nextInt());
        orderItemPojo.setQuantity(new Random().nextInt());
        orderItemPojo.setProductId(new Random().nextInt());
        orderItemPojo.setSellingPrice(new Random().nextDouble());
        dao.addOrderInCart(orderItemPojo);
    }

    @Test
    public void daoSelectAllByOrderId(){
        Integer orderId = new Random().nextInt();
        for(int i = 0 ;i < 5 ;i++)
            insertHelper(new Random().nextInt(),orderId);
        assertEquals(dao.getByOrderId(orderId).size(),5);
    }

    @Test
    public void daoSelectById(){
        OrderItemPojo inputOrderItem = insertHelper(new Random().nextInt(),new Random().nextInt());
        OrderItemPojo outputItem = dao.getOrderItemDataById(inputOrderItem.getId());
        assertEquals(inputOrderItem,outputItem);
    }

    @Test
    public void daoSelectByProductOrderId(){
        OrderItemPojo inputOrderItem = insertHelper(new Random().nextInt(),new Random().nextInt());
        OrderItemPojo outputItem = dao.getByProductOrderId(inputOrderItem.getProductId(), inputOrderItem.getOrderId());
        assertEquals(inputOrderItem,outputItem);
    }
    @Test
    public void daoDeleteById(){
        OrderItemPojo inputItem = insertHelper(new Random().nextInt(),new Random().nextInt());
        dao.deleteById(inputItem.getId());
        OrderItemPojo deletedOrder = dao.getOrderItemDataById(inputItem.getId());
        assertNull(deletedOrder);
    }

    public OrderItemPojo insertHelper(Integer productId,Integer orderId){
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setQuantity(new Random().nextInt());
        orderItemPojo.setProductId(productId);
        orderItemPojo.setSellingPrice(new Random().nextDouble());
        dao.addOrderInCart(orderItemPojo);
        return orderItemPojo;
    }
    
}
