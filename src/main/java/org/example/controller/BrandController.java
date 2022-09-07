package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.dto.BrandDto;
import org.example.model.data.BrandData;
import org.example.model.forms.BrandForm;
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
    public BrandData getBrand(@PathVariable int id){
        return brandDto.get(id);
    }

    @ApiOperation(value = "Adds Brand")
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public void add(@RequestBody List<BrandForm> form) {
        brandDto.add(form);
    }

    @ApiOperation(value = "Gets list of all Brands")
    @RequestMapping(path = "/viewAll", method = RequestMethod.GET)
    public List<BrandData> getAll() {
        return brandDto.fetchAll();
    }

    @ApiOperation(value = "Update a brand by id")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm brandForm) {
        brandDto.update(id, brandForm);
    }

    @ApiOperation(value = "Delete a brand by Id")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        brandDto.delete(id);
    }
}
