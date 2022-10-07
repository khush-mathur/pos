package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
public class BrandService {

    @Autowired
    BrandDao brandDao;

    @Transactional(readOnly = true)
    public BrandPojo getBrandById(Integer id) throws ApiException {
        BrandPojo pojo = brandDao.selectById(id);
        if(pojo==null)
            throw new ApiException("No Brand Category present with given input "+ id);
        return pojo;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void uploadBrands(List<BrandPojo> inputBrandList) throws ApiException {
        for (BrandPojo inputBrand: inputBrandList) {
            if (validate(inputBrand) && !BrandExists(inputBrand))
                brandDao.insert(inputBrand);
        }
    }
    @Transactional(rollbackFor = ApiException.class)
    public List<BrandPojo> getByBrandAndCategory(String brandName,String category) throws ApiException{
        if(brandName.isEmpty() && category.isEmpty())
            return brandDao.selectAll();
        else if(brandName.isEmpty())
            return brandDao.selectByCategory(category);
        else if (category.isEmpty())
            return brandDao.selectByBrand(brandName);
        List<BrandPojo> brandList = new ArrayList<>();
        BrandPojo brand = brandDao.selectByBrandCategory(brandName,category);
        if()
        brandList.add(brand);
        return brandList;
    }

    @Transactional(readOnly = true)
    public List<BrandPojo> getAllBrands(){
        return brandDao.selectAll();
    }

    @Transactional(rollbackFor = ApiException.class)
    public BrandPojo update(Integer id,BrandPojo inputBrand) throws ApiException {
        BrandPojo existingBrand = getBrandById(id);
        if(validate(inputBrand) && !BrandExists(inputBrand)){
            existingBrand.setBrandName(inputBrand.getBrandName());
            existingBrand.setCategory(inputBrand.getCategory());
        }
        return existingBrand;
    }

    private boolean BrandExists(BrandPojo inputBrand) throws ApiException {
        if (brandDao.selectByBrandCategory(inputBrand.getBrandName(), inputBrand.getCategory())!=null){
                throw new ApiException("Brand Already Exists with name = " +
                        inputBrand.getBrandName() + " and category = " + inputBrand.getCategory());
            }
        return false;
    }

    private boolean validate(BrandPojo inputBrand) throws ApiException {
        if(inputBrand == null)
            throw new ApiException("input brand is null");
        else if (inputBrand.getBrandName().isEmpty())
            throw new ApiException("Invalid input : Brand name is empty");
        else if(inputBrand.getCategory().isEmpty())
            throw new ApiException("Invalid input : category is empty");
        return true;
    }
    public void add(BrandPojo inputBrand) throws ApiException {
        if (validate(inputBrand) && !BrandExists(inputBrand))
            brandDao.insert(inputBrand);
    }
}

