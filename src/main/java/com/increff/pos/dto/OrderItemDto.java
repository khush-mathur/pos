package com.increff.pos.dto;

import com.increff.pos.dto.helper.OrderItemDtoHelper;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.forms.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderItemDto {

    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ProductService productService;
    @Autowired
    InventoryService inventoryService;


    public List<OrderItemData> getCartItems() throws ApiException {
        List<OrderItemData> orderItemList = new ArrayList<>();
        for(OrderItemPojo pojo: orderItemService.getCartItem()) {
            ProductPojo product = productService.getProductById(pojo.getProductId());
            OrderItemData itemData = OrderItemDtoHelper.convertToData(pojo);
            itemData.setBarcode(product.getBarcode());
            itemData.setProductName(product.getName());
            orderItemList.add(itemData);
        }
        return orderItemList;
    }
    public void addOrderItem(Integer orderId,OrderItemForm orderItemForm) throws ApiException{
        ProductPojo product= productService.getProductByBarcode(orderItemForm.getBarcode());
        if (validate(orderItemForm) && product!=null) {
            OrderItemPojo pojo = OrderItemDtoHelper.convertToPojo(orderItemForm);
            pojo.setOrderId(orderId);
            pojo.setSellingPrice(product.getMrp());
            pojo.setProductId(product.getId());
            orderItemService.addOrderItem(pojo);
        }
        else
            throw new ApiException("No product with Barcode "+ orderItemForm.getBarcode()+" present in master data");
    }


    public void updateOrderItem(Integer id, OrderItemForm orderItemForm) throws ApiException {
        if(validate(orderItemForm)) {
            OrderItemPojo pojo = OrderItemDtoHelper.convertToPojo(orderItemForm);
            orderItemService.updateOrderItem(id, pojo);
        }
    }

    private boolean validate(OrderItemForm form) throws ApiException{
        if(form==null)
            throw new ApiException("Input is null, please do enter input");
        else if (form.getBarcode() == null || form.getBarcode().isEmpty())
            throw new ApiException("Invalid input: Entered barcode is empty");
        else if (form.getQuantity()<=0)
            throw new ApiException("Invalid input: Quantity cannot be less than 1");
        return true;
    }

    public void deleteFromCart(Integer id) throws ApiException{
        OrderItemPojo orderItem = orderItemService.getOrderItemById(id);
        InventoryPojo inventory = inventoryService.get(orderItem.getProductId());
        if(orderItem==null)
            throw new ApiException("No Item in cart present with given ID " + id);
        orderItemService.delete(orderItem,inventory);
    }

    public OrderItemData getOrderItem(Integer id) throws ApiException{
        OrderItemPojo pojo = orderItemService.getOrderItemById(id);
        if(pojo==null)
            throw new ApiException("No order Item by given Id is present");
        ProductPojo product = productService.getProductById(pojo.getProductId());
        OrderItemData orderItem =   OrderItemDtoHelper.convertToData(pojo);
        orderItem.setBarcode(product.getBarcode());
        orderItem.setProductName(product.getName());
        return orderItem;
    }
}
