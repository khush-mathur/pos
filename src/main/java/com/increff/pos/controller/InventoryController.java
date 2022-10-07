package com.increff.pos.controller;

import com.increff.pos.dto.InventoryDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.forms.InventoryForm;
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
    @RequestMapping(path = "/view/{barcode}",method = RequestMethod.GET)
    public InventoryData getQuantity (@PathVariable String barcode) throws ApiException{
        return inventoryDto.get(barcode);
    }

    @ApiOperation(value = "Updates the inventory by certain quantity")
    @RequestMapping(path = "/update/{barcode}", method = RequestMethod.PUT)
    public void update(@PathVariable String barcode,@RequestBody Integer quantity) throws ApiException {
        inventoryDto.update(barcode,quantity);
    }

    @ApiOperation(value = "Adds Inventory for a Product")
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public void addInInventory(@RequestBody InventoryForm form) throws ApiException {
        inventoryDto.add(form);
    }

    @ApiOperation(value = "Gets list of all the Inventory")
    @RequestMapping(path = "/viewAll", method = RequestMethod.GET)
    public List<InventoryData> getInventoryList() throws ApiException {
        return inventoryDto.fetchAll();
    }
}
