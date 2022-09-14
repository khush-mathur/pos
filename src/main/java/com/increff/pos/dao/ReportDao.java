package com.increff.pos.dao;

import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ReportDao extends AbstractDao{

    private static String SELECT_PRODUCT_BY_BRAND_CATEGORY = "select p from Product p where brandCategory = :brandCategory";
    private static String SELECT_INVENTORY_BY_PRODUCT_ID = "select p from Inventory p where id=:id";
    public List<ProductPojo> getProductByBrandCategory(Integer brandCategory) {
        TypedQuery<ProductPojo> query = em().createQuery(SELECT_PRODUCT_BY_BRAND_CATEGORY,ProductPojo.class);
        query.setParameter("brandCategory",brandCategory);
        return query.getResultList();
    }

    public InventoryPojo getInventoryByProductId(Integer id) {
        TypedQuery<InventoryPojo> query= em().createQuery(SELECT_INVENTORY_BY_PRODUCT_ID, InventoryPojo.class);
        query.setParameter("id",id);
        return getSingleRow(query);
    }
}
