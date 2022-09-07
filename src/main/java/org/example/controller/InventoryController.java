package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.dto.InventoryDto;
import org.example.pojo.InventoryPojo;
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
    public InventoryPojo getQuantity(@PathVariable int id){
        return inventoryDto.get(id);
    }

    @ApiOperation(value = "increases the inventory by certain quantity")
    @RequestMapping(path = "/add/{id}", method = RequestMethod.POST)
    public void add(@PathVariable int id,@RequestBody int quantity) {
        inventoryDto.add(id,quantity);
    }
    @ApiOperation(value = "Decreases the inventory by certain quantity")
    @RequestMapping(path = "/remove/{id}", method = RequestMethod.POST)
    public void remove(@PathVariable int id,@RequestBody int quantity) {
        inventoryDto.remove(id,quantity);
    }

    @ApiOperation(value = "Gets list of all the Inventory")
    @RequestMapping(path = "/viewAll", method = RequestMethod.GET)
    public List<InventoryPojo> getInventoryList() {
        return inventoryDto.fetchAll();
    }
}
