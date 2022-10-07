package com.increff.pos.controller;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItemData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/order")
public class OrderController {
    @Autowired
    OrderDto orderDto;

    @ApiOperation(value = "Place the order")
    @RequestMapping(path="/placeOrder/{orderId}",method = RequestMethod.POST)
    public void placeOrder(@PathVariable Integer orderId) throws ApiException{
        orderDto.placeOrder(orderId);
    }

    @ApiOperation(value = "Gets list of all Orders")
    @RequestMapping(path = "/viewAll", method = RequestMethod.GET)
    public List<OrderData> getAll() {
        return orderDto.fetchAll();
    }

    @ApiOperation(value = "Get Order Items by order id")
    @RequestMapping(path = "/view/{id}",method = RequestMethod.GET)
    public List<OrderItemData> getOrderItemByOrderId(@PathVariable Integer id) throws ApiException {
        return orderDto.getItemListByOrderId(id);
    }
    @ApiOperation(value = "generate the invoice")
    @RequestMapping(value = "/invoice/{orderId}", method = RequestMethod.GET)
    public void generateInvoice(@PathVariable Integer orderId) throws ApiException {
        orderDto.generateInvoice(orderId);
    }

    @ApiOperation(value = "Add New Order")
    @RequestMapping(value = "/new-order", method = RequestMethod.GET)
    public OrderData generateInvoice() throws ApiException {
        return orderDto.addNewOrder();
    }

    @ApiOperation(value = "Delete a Order by Id")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id){
        orderDto.deleteOrder(id);
    }
}
