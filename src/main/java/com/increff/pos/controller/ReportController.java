package com.increff.pos.controller;

import com.increff.pos.dto.ReportDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.BrandReportData;
import com.increff.pos.model.data.InventoryReportData;
import com.increff.pos.model.data.SalesReportData;
import com.increff.pos.model.forms.SalesReportForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/report")
public class ReportController {
    @Autowired
    ReportDto reportDto;

    @ApiOperation(value = "Get Brand Report")
    @RequestMapping(path = "/brand-report",method = RequestMethod.GET)
    public List<BrandReportData> getBrandReport() throws ApiException {
        return reportDto.getBrandReport();
    }

    @ApiOperation(value = "Get Inventory Report")
    @RequestMapping(path = "/inventory-report",method =RequestMethod.GET)
    public List<InventoryReportData> getInventoryReport() throws ApiException {
        return  reportDto.getInventoryReport();
    }

    @ApiOperation(value = "Get Sales Report")
    @RequestMapping(path = "/sales-report",method =RequestMethod.POST)
    public List<SalesReportData> getSalesReport(@RequestBody SalesReportForm form) throws ApiException {
        return reportDto.getSalesReport(form);
    }
}
