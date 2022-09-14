package com.increff.pos.dto;

import com.increff.pos.dto.helper.OrderItemDtoHelper;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.forms.OrderItemForm;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
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


    public List<OrderItemData> getCartItems() throws ApiException {
        List<OrderItemData> orderItemList = new ArrayList<>();
        for(OrderItemPojo pojo: orderItemService.getCartItem()) {
            OrderItemData itemData = OrderItemDtoHelper.convertToData(pojo);
            itemData.setBarcode(productService.getProductById(pojo.getProductId()).getBarcode());
            orderItemList.add(itemData);
        }
        return orderItemList;
    }
    public void addOrderItem(OrderItemForm orderItemForm) throws ApiException{
        ProductPojo product= productService.getProductByBarcode(orderItemForm.getBarcode());
        if (validate(orderItemForm) && product!=null) {
            OrderItemPojo pojo = OrderItemDtoHelper.convertToPojo(orderItemForm);
            pojo.setSellingPrice(product.getMrp());
            pojo.setProductId(product.getId());
            orderItemService.addOrderItem(pojo);
        }
        else
            throw new ApiException("No product with Barcode "+ orderItemForm.getBarcode()+" present in master data");
    }
    public void updateOrderItem(Integer id, OrderItemForm orderItemForm) throws ApiException {
        OrderItemPojo pojo = OrderItemDtoHelper.convertToPojo(orderItemForm);
        orderItemService.updateOrderItem(id,pojo);
    }

    private boolean validate(OrderItemForm form) throws ApiException{
        if(form==null)
            throw new ApiException("Input is null, please do enter input");
        else if (form.getBarcode().isEmpty())
            throw new ApiException("Invalid input: Entered barcode is empty");
        else if (form.getQuantity()<=0)
            throw new ApiException("Invalid input: Quantity cannot be less than 1");
        return true;
    }
}
