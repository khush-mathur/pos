package com.increff.pos.controller;

import com.increff.pos.dto.InventoryDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.pojo.InventoryPojo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/inventory")
public class InventoryController {
    @Autowired
    InventoryDto inventoryDto;

    @ApiOperation(value = "get quantity of a Product by Id")
    @RequestMapping(path = "/view/{id}",method = RequestMethod.GET)
    public InventoryPojo getQuantity (@PathVariable Integer id) throws ApiException{
        return inventoryDto.get(id);
    }

    @ApiOperation(value = "Updates the inventory by certain quantity")
    @RequestMapping(path = "/update/{productId}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer productId,@RequestBody Integer quantity) throws ApiException {
        inventoryDto.update(productId,quantity);
    }

    @ApiOperation(value = "Adds Inventory for a Product")
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public void addInInventory(@RequestBody InventoryData inventoryData) throws ApiException {
        inventoryDto.add(inventoryData);
    }

    @ApiOperation(value = "Gets list of all the Inventory")
    @RequestMapping(path = "/viewAll", method = RequestMethod.GET)
    public List<InventoryPojo> getInventoryList() {
        return inventoryDto.fetchAll();
    }
}
