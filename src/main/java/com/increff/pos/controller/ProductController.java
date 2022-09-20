package com.increff.pos.controller;

import com.increff.pos.exception.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.increff.pos.dto.ProductDto;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.forms.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    ProductDto productDto;

    @ApiOperation(value = "Adds Product")
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public void add(@RequestBody List<ProductForm> form) throws ApiException{
        productDto.add(form);
    }

    @ApiOperation(value = "Fetch Product")
    @RequestMapping(path = "/view/{id}", method = RequestMethod.GET)
    public ProductData get(@PathVariable Integer id) throws ApiException {
        return productDto.get(id);
    }

    @ApiOperation(value ="Fetches all the products")
    @RequestMapping(path = "/viewAll",method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException{
        return productDto.getAll();
    }

    @ApiOperation(value= "Updates a Existing Product")
    @RequestMapping(path="/update/{id}",method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody ProductForm form) throws ApiException {
        productDto.update(id,form);

    }
    @ApiOperation(value= "Delete a Product by ID")
    @RequestMapping(path="/delete/{id}",method = RequestMethod.DELETE)
    public void update(@PathVariable Integer id){
        productDto.delete(id);
    }

}
