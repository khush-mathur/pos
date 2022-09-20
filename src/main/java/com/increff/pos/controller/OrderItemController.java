package com.increff.pos.controller;

import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.forms.OrderItemForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping(path="/cart")
public class OrderItemController {
    @Autowired
    OrderItemDto orderItemDto;



    @ApiOperation(value = "Get a Order Item by Id")
    @RequestMapping(path = "/view/{id}",method =RequestMethod.GET)
    public OrderItemData getOrderItem(@PathVariable Integer id) throws ApiException {
        return orderItemDto.getOrderItem(id);
    }

    @ApiOperation(value = "Add a Order Item in cart")
    @RequestMapping(path = "/add/{orderId}",method = RequestMethod.POST)
    public void addOrderItem(@PathVariable Integer orderId,@RequestBody OrderItemForm orderItemForm) throws ApiException{
         orderItemDto.addOrderItem(orderId,orderItemForm);
    }

    @ApiOperation(value = "Update a item in the cart")
    @RequestMapping(path = "/update/{id}",method = RequestMethod.PUT)
    public void updateOrderItem(@PathVariable Integer id,@RequestBody OrderItemForm orderItemForm) throws ApiException{
        orderItemDto.updateOrderItem(id, orderItemForm);
    }

    @ApiOperation(value = "Delete a item from cart")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) throws ApiException{
        orderItemDto.deleteFromCart(id);
    }
}
