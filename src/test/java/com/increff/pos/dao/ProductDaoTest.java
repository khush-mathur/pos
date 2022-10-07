package com.increff.pos.dao;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.pojo.ProductPojo;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest extends AbstractUnitTest {

    @Resource
    ProductDao dao;

    @Test
    public void daoAddProduct(){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(RandomStringUtils.random(10, true, true));
        productPojo.setBrandCategory(new Random().nextInt());
        productPojo.setMrp(new Random().nextDouble());
        productPojo.setBarcode(RandomStringUtils.random(10, true, true));
        dao.add(productPojo);
    }

    @Test
    public void getAll(){
        for(int i =0 ; i<5 ; i++) {
            insertHelper();
        }
        assertEquals(dao.getAll().size(),5);
    }

    @Test
    public void daoSelectByBarcode(){
        ProductPojo inputProduct = insertHelper();
        ProductPojo output = dao.getByBarcode(inputProduct.getBarcode());
        assertEquals(inputProduct,output);
    }

    @Test
    public void daoSelectById(){
        ProductPojo inputProduct = insertHelper();
        ProductPojo output = dao.getByID(inputProduct.getId());
        assertEquals(inputProduct,output);
    }

    public ProductPojo insertHelper(){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(RandomStringUtils.random(10, true, true));
        productPojo.setBrandCategory(new Random().nextInt());
        productPojo.setMrp(new Random().nextDouble());
        productPojo.setBarcode(RandomStringUtils.random(10, true, true));
        dao.add(productPojo);
        return productPojo;
    }
}
