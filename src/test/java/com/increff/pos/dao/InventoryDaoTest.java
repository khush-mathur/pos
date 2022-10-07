package com.increff.pos.dao;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.pojo.InventoryPojo;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class InventoryDaoTest extends AbstractUnitTest {

    @Resource
    InventoryDao dao;

    @Test
    public void daoInsert(){
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setProductId(new Random().nextInt());
        inventoryPojo.setQuantity(new Random().nextInt());
        dao.add(inventoryPojo);
    }

    @Test
    public void daoSelectAll(){
        for(int i = 0 ;i < 5 ;i++)
            insertHelper();
        assertEquals(dao.getAll().size(),5);
    }

    @Test
    public void daoSelectById(){
        InventoryPojo inputInventory = insertHelper();
        InventoryPojo output = dao.getByProductId(inputInventory.getProductId());
        assertEquals(inputInventory,output);
    }

    public InventoryPojo insertHelper(){
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setProductId(new Random().nextInt());
        inventoryPojo.setQuantity(new Random().nextInt());
        dao.add(inventoryPojo);
        return inventoryPojo;
    }
}
