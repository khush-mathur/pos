package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.dto.ProductDto;
import org.example.model.data.ProductData;
import org.example.model.forms.ProductForm;
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
    public void add(@RequestBody List<ProductForm> form) {
        productDto.add(form);
    }

    @ApiOperation(value = "Fetch Product")
    @RequestMapping(path = "/view/{id}", method = RequestMethod.GET)
    public ProductForm get(@PathVariable int id) {
        return productDto.get(id);
    }

    @ApiOperation(value ="Fetches all the products")
    @RequestMapping(path = "/viewAll",method = RequestMethod.GET)
    public List<ProductData> getAll(){
        return productDto.getAll();
    }

    @ApiOperation(value= "Updates a Existing Product")
    @RequestMapping(path="/update/{id}",method = RequestMethod.POST)
    public void update(@PathVariable int id, @RequestBody ProductForm form){
        productDto.update(id,form);

    }
    @ApiOperation(value= "Delete a Product by ID")
    @RequestMapping(path="/delete/{id}",method = RequestMethod.DELETE)
    public void update(@PathVariable int id){
        productDto.delete(id);
    }

}
