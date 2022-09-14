package com.increff.pos.dto;

import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.forms.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.BrandService;
import com.increff.pos.dto.helper.BrandDtoHelper;
import com.increff.pos.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BrandDto {
    @Autowired
    private BrandService brandService;

    public BrandData get(Integer id) throws ApiException {
        return BrandDtoHelper.convertToBrandData(brandService.getBrandById(id));
    }

    public void upload(List<BrandForm> formList) throws ApiException {
        List<BrandPojo> brandList= new ArrayList<>();
        for (BrandForm p : formList) {
            brandList.add(BrandDtoHelper.convertToBrandPojo(p));
        }
        brandService.uploadBrands(brandList);
    }

    public void add(BrandForm form) throws ApiException {
        brandService.add(BrandDtoHelper.convertToBrandPojo(form));
    }


    public List<BrandData> fetchAll() {
        List<BrandPojo> list = brandService.getAllBrands();
        List<BrandData> list2 = new ArrayList<>();
        for (BrandPojo p : list) {
            list2.add(BrandDtoHelper.convertToBrandData(p));
        }
        return list2;
    }



    public BrandPojo update(Integer id, BrandForm brandForm) throws ApiException {
        BrandPojo brand = BrandDtoHelper.convertToBrandPojo(brandForm);
        return brandService.update(id,brand);
    }

    public void delete(Integer id) {
        brandService.delete(id);
    }


}
