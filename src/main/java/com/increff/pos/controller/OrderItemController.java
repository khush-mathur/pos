package com.increff.pos.controller;

import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.forms.OrderItemForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path="/cart")
public class OrderItemController {
    @Autowired
    OrderItemDto orderItemDto;

    @ApiOperation(value = "Get a Order Item by order id")
    @RequestMapping(path = "/viewCart",method = RequestMethod.GET)
    public List<OrderItemData> getCartItems() throws ApiException {
        return orderItemDto.getCartItems();
    }

    @ApiOperation(value = "Add a Order Item in cart")
    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public void addOrderItem(@RequestBody OrderItemForm orderItemForm) throws ApiException{
         orderItemDto.addOrderItem(orderItemForm);
    }

    @ApiOperation(value = "Update a item in the cart")
    @RequestMapping(path = "/update/{id}",method = RequestMethod.PUT)
    public void updateOrderItem(@PathVariable Integer id,@RequestBody OrderItemForm orderItemForm) throws ApiException{
        orderItemDto.updateOrderItem(id, orderItemForm);
    }
}
