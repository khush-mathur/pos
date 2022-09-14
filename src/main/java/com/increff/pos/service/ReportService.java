package com.increff.pos.service;

import com.increff.pos.dao.ReportDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.BrandReportData;
import com.increff.pos.model.data.InventoryReportData;
import com.increff.pos.model.data.SalesReportData;
import com.increff.pos.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private OrderItemService orderitemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @Transactional(rollbackOn = ApiException.class)
    public List<BrandReportData> getBrandReport(List<BrandPojo> brandList) {
        List<BrandReportData> brandReport = new ArrayList<>();
        for(BrandPojo brand :brandList){
            BrandReportData reportData = new BrandReportData();
            reportData.setBrandName(brand.getBrandName());
            reportData.setCategory(brand.getCategory());
            brandReport.add(reportData);
        }
        return brandReport;
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<InventoryReportData> getInventory() throws ApiException {
        List<InventoryReportData> inventoryReport = new ArrayList<>();
        for (BrandPojo brand : brandService.getAllBrands()){
            Integer quantity = 0;
            for(ProductPojo product : reportDao.getProductByBrandCategory(brand.getId())){
                InventoryPojo productInventory = reportDao.getInventoryByProductId(product.getId());
                quantity += (productInventory!=null) ? productInventory.getQuantity() :0 ;
            }
            //if(quantity>0){}
            InventoryReportData inventory = new InventoryReportData();
            inventory.setBrandName(brand.getBrandName());
            inventory.setCategory(brand.getCategory());
            inventory.setQuantity(quantity);
            inventoryReport.add(inventory);
        }
        return inventoryReport;
    }

    @Transactional
    public List<SalesReportData> getSalesReport(SalesReportPojo salesPojo) throws ApiException{
        List<SalesReportData> salesReport = new ArrayList<>();
        List<BrandPojo> brands = brandService.getByBrandAndCategory(salesPojo.getBrand(), salesPojo.getCategory());
        for(BrandPojo brand :brands){
            SalesReportData brandSale = new SalesReportData();
            brandSale.setCategory(brand.getCategory());
            for(OrderPojo order : getOrdersBetweenTime(salesPojo)){
                for(OrderItemPojo item :orderitemService.getByOrderId(order.getId())){
                    if (productService.getProductById(item.getProductId()).getBrandCategory() == brand.getId()) {
                        brandSale.setQuantity(brandSale.getQuantity()+item.getQuantity());
                        brandSale.setRevenue(brandSale.getRevenue() + (item.getSellingPrice() * item.getQuantity()));
                    }
                }
            }
            salesReport.add(brandSale);
        }
        return salesReport;
    }

    public List<OrderPojo> getOrdersBetweenTime(SalesReportPojo salesPojo){
        List<OrderPojo> orderList = new ArrayList<>();
        for(OrderPojo order : orderService.getAllOrders()){
            ZonedDateTime orderTime= order.getDateTime();
            if(orderTime.isAfter(salesPojo.getStartTime()) && orderTime.isBefore(salesPojo.getEndTime())){
                orderList.add(order);
            }
        }
        return orderList;
    }
}
