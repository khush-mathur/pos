package com.increff.pos.dao;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.pojo.BrandPojo;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;


public class BrandDaoTest extends AbstractUnitTest {
    @Resource
    private BrandDao dao;

    @Test
    public void daoInsert(){
        BrandPojo p = new BrandPojo();
        String category = RandomStringUtils.random(10, true, true);
        String brand = RandomStringUtils.random(10, true, true);
        p.setCategory(category);
        p.setBrandName(brand);
        dao.insert(p);
    }

    @Test
    public void daoSelectAll(){
        for(int i = 0 ;i < 5 ;i++)
            insertHelper();
        assertEquals(dao.selectAll().size(),5);
    }

    @Test
    public void daoSelectByBrandCategory(){
        BrandPojo inputBrand = insertHelper();
        BrandPojo output = dao.selectByBrandCategory(inputBrand.getBrandName(), inputBrand.getCategory());
        assertEquals(inputBrand,output);
    }

    @Test
    public void daoSelectById(){
        BrandPojo inputBrand = insertHelper();
        BrandPojo output = dao.selectById(inputBrand.getId());
        assertEquals(inputBrand,output);
    }

    @Test
    public void daoSelectByBrand(){
        String testBrandName = "junit-brand";
        for(int i =0 ; i<5 ; i++) {
            BrandPojo pojo = new BrandPojo();
            pojo.setCategory(RandomStringUtils.random(10, true, true));
            pojo.setBrandName(testBrandName);
            dao.insert(pojo);
        }
        assertEquals(dao.selectByBrand(testBrandName).size(),5);
    }

    @Test
    public void daoSelectByCategory(){
        String testCategoryName = "junit-category";
        for(int i =0 ; i<5 ; i++) {
            BrandPojo pojo = new BrandPojo();
            pojo.setBrandName(RandomStringUtils.random(10, true, true));
            pojo.setCategory(testCategoryName);
            dao.insert(pojo);
        }
        assertEquals(dao.selectByCategory(testCategoryName).size(),5);
    }

    public BrandPojo insertHelper(){
        BrandPojo brandPojo = new BrandPojo();
        String category = RandomStringUtils.random(10, true, true);
        String brand = RandomStringUtils.random(10, true, true);
        brandPojo.setCategory(category);
        brandPojo.setBrandName(brand);
        dao.insert(brandPojo);
        return brandPojo;
    }
}
