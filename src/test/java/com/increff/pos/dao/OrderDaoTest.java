package com.increff.pos.dao;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.pojo.OrderPojo;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class OrderDaoTest extends AbstractUnitTest {
    
    @Resource
    OrderDao dao;
    
    @Test
    public void daoAdd(){
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setStatus("pending");
        orderPojo.setDateTime(ZonedDateTime.now());
        dao.add(orderPojo);
    }

    @Test
    public void daoSelectAll(){
        for(int i = 0 ;i < 5 ;i++)
            insertHelper();
        assertEquals(dao.selectAll().size(),5);
    }
    
    @Test
    public void daoSelectById(){
        OrderPojo inputOrder = insertHelper();
        OrderPojo output = dao.getByID(inputOrder.getId());
        assertEquals(inputOrder,output);
    }

    @Test
    public void daoDeleteById(){
        OrderPojo inputOrder = insertHelper();
        dao.delete(inputOrder.getId());
        OrderPojo deletedOrder = dao.getByID(inputOrder.getId());
        assertNull(deletedOrder);
    }

    public OrderPojo insertHelper(){
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setStatus("pending");
        orderPojo.setDateTime(ZonedDateTime.now());
        return dao.add(orderPojo);
    }
}
