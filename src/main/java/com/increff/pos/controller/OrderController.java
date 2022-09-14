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

//    @ApiOperation(value = "Get a order by ID")
//    @RequestMapping(path = "/view/{id}",method = RequestMethod.GET)
//    public OrderData getBrand(@PathVariable Integer id) throws ApiException {
//        return orderDto.get(id);
//    }

    @ApiOperation(value = "Place the order")
    @RequestMapping(path="/placeOrder",method = RequestMethod.POST)
    public List<OrderItemData> placeOrder() throws ApiException{
        return orderDto.placeOrder();
    }

    @ApiOperation(value = "Gets list of all Orders")
    @RequestMapping(path = "/viewAll", method = RequestMethod.GET)
    public List<OrderData> getAll() {
        return orderDto.fetchAll();
    }

    @ApiOperation(value = "Get Order Items by order id")
    @RequestMapping(path = "/view/{id}",method = RequestMethod.GET)
    public List<OrderItemData> getOrderItemByOrderId(@PathVariable Integer id) throws ApiException {
        return orderDto.getByOrderId(id);
    }
    @ApiOperation(value = "generate the invoice")
    @RequestMapping(value = "/invoice/{orderId}", method = RequestMethod.GET)
    public void generateInvoice(@PathVariable int orderId) throws ApiException {
        orderDto.generateInvoice(orderId);
    }
}
