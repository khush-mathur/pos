package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.BrandReportData;
import com.increff.pos.model.data.InventoryReportData;
import com.increff.pos.model.data.SalesReportData;
import com.increff.pos.model.forms.SalesReportForm;
import com.increff.pos.pojo.SalesReportPojo;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import com.increff.pos.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ReportDto {

    @Autowired
    ReportService reportService;
    @Autowired
    BrandService brandService;

    @Autowired
    ProductService productService;

    @Autowired
    InventoryService inventoryService;

    final private static DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    public List<BrandReportData> getBrandReport() throws ApiException {
        return reportService.getBrandReport(brandService.getAllBrands());
    }

    public List<InventoryReportData> getInventoryReport() throws  ApiException{
        return reportService.getInventory();
    }

    public List<SalesReportData> getSalesReport(SalesReportForm salesReportForm) throws ApiException{
        if(!validateInput(salesReportForm)) {
            throw new ApiException("Invalid Input");
        }
        SalesReportPojo pojo = new SalesReportPojo();
        pojo.setStartTime(LocalDateTime.parse(salesReportForm.getStartDate(), formatter).atZone(ZoneId.systemDefault()));
        pojo.setEndTime(LocalDateTime.parse(salesReportForm.getEndDate(), formatter).atZone(ZoneId.systemDefault()));
        pojo.setBrand(salesReportForm.getBrand());
        pojo.setCategory(salesReportForm.getCategory());
        if (pojo.getStartTime().isAfter(pojo.getEndTime()))
            throw new ApiException("Invalid input : start date can't be after end date");
        return reportService.getSalesReport(normalise(pojo));
    }
    private SalesReportPojo normalise (SalesReportPojo pojo){
        pojo.setBrand(pojo.getBrand().trim().toLowerCase());
        pojo.setCategory(pojo.getCategory().trim().toLowerCase());
        return pojo;
    }

    /**
     *
     * TODO :: check this
     */
    private boolean validateInput(SalesReportForm inputForm) throws ApiException {
        if(inputForm.getStartDate()==null)
            throw new ApiException("Please enter a valid Start Date");
        else if(inputForm.getEndDate()==null)
            throw new ApiException("Please enter a valid End Date");
        else if (brandService.getByBrandAndCategory(inputForm.getBrand(),"") == null)
            throw new ApiException("Please enter a valid Brand Name");
        else if (brandService.getByBrandAndCategory("",inputForm.getCategory()) == null)
            throw new ApiException("Please enter a valid category Name");
        return true;
    }
}
