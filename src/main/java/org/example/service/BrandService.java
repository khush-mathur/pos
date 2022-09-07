package org.example.service;

import org.example.dao.BrandDao;
import org.example.exception.ApiException;
import org.example.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BrandService {

    @Autowired
    BrandDao brandDao;

    public BrandPojo getBrandById(int id){
        BrandPojo pojo = brandDao.select(id);
        if(pojo==null)
            throw new ApiException("No Brand Category present with given input"+ id);
        return pojo;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void addBrands(List<BrandPojo> inputBrandList) {
        for (BrandPojo inputBrand: inputBrandList) {
            if (validate(inputBrand) && !BrandExists(inputBrand))
                brandDao.insert(inputBrand);
        }
    }

    public List<BrandPojo> getAllBrands(){
        return brandDao.selectAll();
    }

    @Transactional
    public BrandPojo update(int id,BrandPojo inputBrand) throws ApiException {
        BrandPojo existingBrand = getBrandById(id);
        if(validate(inputBrand) && !BrandExists(inputBrand)){
            existingBrand.setBrandName(inputBrand.getBrandName());
            existingBrand.setCategory(inputBrand.getCategory());
            brandDao.update(existingBrand);
        }
        return existingBrand;
    }

    /**
     *
     * TODO:: handle deletion of product also with checking whether id exists or not.
     */
    public void delete(int id) {
        brandDao.delete(id);
    }
    private boolean BrandExists(BrandPojo inputBrand) throws ApiException {
        for (BrandPojo existingBrand : getAllBrands()) {
            if(existingBrand.getBrandName().equals(inputBrand.getBrandName()) &&
                    existingBrand.getCategory().equals(inputBrand.getCategory())) {
                throw new ApiException("Brand Already Exists with name" +
                        inputBrand.getBrandName() + " and category = " + inputBrand.getCategory());
            }
        }
        return false;
    }

    private boolean validate(BrandPojo inputBrand) throws ApiException {
        if (inputBrand.getBrandName().isEmpty() || inputBrand.getCategory().isEmpty())
            throw new ApiException("Invalid input : Input is empty for " +
                inputBrand.getBrandName() + " and category = " + inputBrand.getCategory());
        return true;
    }

}

