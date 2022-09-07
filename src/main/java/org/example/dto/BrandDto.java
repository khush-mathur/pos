package org.example.dto;

import org.example.dto.helper.BrandDtoHelper;
import org.example.exception.ApiException;
import org.example.model.data.BrandData;
import org.example.model.forms.BrandForm;
import org.example.pojo.BrandPojo;
import org.example.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BrandDto {

    @Autowired
    BrandService brandService;
    @Autowired
    BrandDtoHelper brandDtoHelper;

    public BrandData get(int id) {
        return brandDtoHelper.convertToBrandData(brandService.getBrandById(id));
    }

    public void add(List<BrandForm> formList) {
        List<BrandPojo> brandList= new ArrayList<>();
        for (BrandForm p : formList) {
            brandList.add(brandDtoHelper.convertToBrandPojo(p));
        }
        brandService.addBrands(brandList);
    }


    public List<BrandData> fetchAll() {
        List<BrandPojo> list = brandService.getAllBrands();
        List<BrandData> list2 = new ArrayList<>();
        for (BrandPojo p : list) {
            list2.add(brandDtoHelper.convertToBrandData(p));
        }
        return list2;
    }



    public BrandPojo update(int id, BrandForm brandForm) throws ApiException {
        BrandPojo brand = brandDtoHelper.convertToBrandPojo(brandForm);
        return brandService.update(id,brand);
    }

    public void delete(int id) {
        brandService.delete(id);
    }
}
