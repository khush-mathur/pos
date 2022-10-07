package com.increff.pos.dto;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.dao.BrandDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.forms.BrandForm;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

public class BrandDtoTest extends AbstractUnitTest {

    @Resource
    BrandDto brandDto;
    @Resource
    BrandDao brandDao;

    @Test
    public void dtoGet() throws ApiException{
        BrandForm form = new BrandForm();
        String brand = RandomStringUtils.random(10, true, true);
        String category = RandomStringUtils.random(10, true, true);
        form.setBrandName(brand);
        form.setCategory(category);
        brandDto.add(form);

        BrandData data = brandDto.get(brandDao.selectByBrandCategory(brand.trim().toLowerCase(),category.trim().toLowerCase()).getId());
        assertEquals(data.getBrandName(),brand.trim().toLowerCase());
        assertEquals(data.getCategory(),category.trim().toLowerCase());
    }

    @Test
    public void dtoAdd() throws ApiException{
        Integer currSize = brandDao.selectAll().size();
        BrandForm form = new BrandForm();
        form.setBrandName(RandomStringUtils.random(10, true, true));
        form.setCategory(RandomStringUtils.random(10, true, true));
        brandDto.add(form);
        assertEquals(brandDao.selectAll().size(),1);
    }

    @Test
    public void dtoGetAll() throws ApiException{
        for(int i =0;i<5;i++){
            BrandForm form = new BrandForm();
            form.setBrandName(RandomStringUtils.random(10, true, true));
            form.setCategory(RandomStringUtils.random(10, true, true));
            brandDto.add(form);
        }
        assertEquals(brandDto.fetchAll().size(),5);
    }

    @Test
    public void dtoUpdate() throws ApiException{
        BrandForm form = new BrandForm();
        form.setBrandName(RandomStringUtils.random(10, true, true));
        form.setCategory(RandomStringUtils.random(10, true, true));
        brandDto.add(form);
        String brandName = RandomStringUtils.random(10, true, true);
        String category = RandomStringUtils.random(10, true, true);
        form.setCategory(category);
        form.setBrandName(brandName);
        brandDto.update(1,form);
        BrandData brandData = brandDto.get(1);
        assertEquals(brandData.getBrandName(),brandName.trim().toLowerCase());
        assertEquals(brandData.getCategory(),category.trim().toLowerCase());
    }
}
