package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BrandService {

    @Autowired
    BrandDao brandDao;

    public BrandPojo getBrandById(Integer id) throws ApiException {
        BrandPojo pojo = brandDao.selectById(id);
        if(pojo==null)
            throw new ApiException("No Brand Category present with given input"+ id);
        return pojo;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void uploadBrands(List<BrandPojo> inputBrandList) throws ApiException {
        for (BrandPojo inputBrand: inputBrandList) {
            if (validate(inputBrand) && !BrandExists(inputBrand))
                brandDao.insert(inputBrand);
        }
    }
    @Transactional(rollbackOn = ApiException.class)
    public List<BrandPojo> getByBrandAndCategory(String brandName,String category) throws ApiException{
        if(brandName.isEmpty() && category.isEmpty())
            throw new ApiException("brandName & category both can't be empty");
        else if(brandName.isEmpty())
            return brandDao.getByCategory(category);
        else if (category.isEmpty())
            return brandDao.getByBrand(brandName);
        List<BrandPojo> brandList = new ArrayList<>();
        brandList.add(brandDao.selectByBrandCategory(brandName,category));
        return brandList;
    }

    public List<BrandPojo> getAllBrands(){
        return brandDao.selectAll();
    }

    @Transactional
    public BrandPojo update(Integer id,BrandPojo inputBrand) throws ApiException {
        BrandPojo existingBrand = getBrandById(id);
        if(validate(inputBrand) && !BrandExists(inputBrand)){
            existingBrand.setBrandName(inputBrand.getBrandName());
            existingBrand.setCategory(inputBrand.getCategory());
//            brandDao.update(existingBrand);
        }
        return existingBrand;
    }

    /**
     *
     * TODO:: handle deletion of product also with checking whether id exists or not.
     */
    public void delete(Integer id) {
        brandDao.delete(id);
    }
    private boolean BrandExists(BrandPojo inputBrand) throws ApiException {
        if (brandDao.selectByBrandCategory(inputBrand.getBrandName(), inputBrand.getCategory())!=null){
                throw new ApiException("Brand Already Exists with name" +
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

