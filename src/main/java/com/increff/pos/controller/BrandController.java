package com.increff.pos.controller;

import com.increff.pos.exception.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.increff.pos.dto.BrandDto;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.forms.BrandForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/brand")
public class BrandController {
    @Autowired
    BrandDto brandDto;

    @ApiOperation(value = "Get a brand by ID")
    @RequestMapping(path = "/view/{id}",method =RequestMethod.GET)
    public BrandData getBrand(@PathVariable Integer id) throws ApiException {
        return brandDto.get(id);
    }

    @ApiOperation(value = "Upload Bulk Brand List")
    @RequestMapping(path = "/bulk-upload", method = RequestMethod.POST)
    public void upload(@RequestBody List<BrandForm> form) throws ApiException {
        brandDto.upload(form);
    }

    @ApiOperation(value = "Adds Brand")
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException {
        brandDto.add(form);
    }

    @ApiOperation(value = "Gets list of all Brands")
    @RequestMapping(path = "/viewAll", method = RequestMethod.GET)
    public List<BrandData> getAll() {
        return brandDto.fetchAll();
    }

    @ApiOperation(value = "Update a brand by id")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody BrandForm brandForm) throws ApiException {
        brandDto.update(id, brandForm);
    }

    @ApiOperation(value = "Delete a brand by Id")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id){
        brandDto.delete(id);
    }
}
