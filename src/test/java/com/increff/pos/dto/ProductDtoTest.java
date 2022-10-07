package com.increff.pos.dto;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.forms.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ProductDtoTest extends AbstractUnitTest {
    @Resource
    ProductDto productDto;
    @Resource
    ProductDao productDao;
    @Resource
    BrandDao brandDao;

    @Test
    public void dtoAdd() throws ApiException {
        Integer currSize = productDao.getAll().size();
        List<ProductForm> formList = new ArrayList<>();
        ProductForm form = new ProductForm();
        form.setBarcode(RandomStringUtils.random(10, true, true));
        form.setName(RandomStringUtils.random(10, true, true));
        form.setMrp(new Random().nextDouble());
        BrandPojo brand = new BrandPojo();
        brand.setBrandName(RandomStringUtils.random(10, true, true));
        brand.setCategory(RandomStringUtils.random(10, true, true));
        brandDao.insert(brand);
        form.setBrandCategory(1);
        formList.add(form);
        productDto.add(formList);
        assertEquals(productDao.getAll().size(),1);
    }

//    @Test
//    public void dto() throws ApiException {
//        Integer currSize = productDao.getAll().size();
//        List<ProductForm> formList = new ArrayList<>();
//        ProductForm form = new ProductForm();
//        form.setBarcode(RandomStringUtils.random(10, true, true));
//        form.setName(RandomStringUtils.random(10, true, true));
//        form.setMrp(new Random().nextDouble());
//        BrandPojo brand = new BrandPojo();
//        brand.setBrandName(RandomStringUtils.random(10, true, true));
//        brand.setCategory(RandomStringUtils.random(10, true, true));
//        brandDao.insert(brand);
//        form.setBrandCategory(1);
//        formList.add(form);
//        productDto.add(formList);
//        assertEquals(productDao.getAll().size(),1);
//    }
}
