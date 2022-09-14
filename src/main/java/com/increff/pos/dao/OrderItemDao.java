package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{
    private static String SELECT_BY_ORDER_ID = "select d from OrderItem d where orderId = :orderId";
    private static String SELECT_BY_PRODUCT_ORDER_ID = "select d from OrderItem d where orderId = :orderId and productId = :productId";

    public List<OrderItemPojo> getByOrderId(Integer orderId) {
        TypedQuery<OrderItemPojo> query = em().createQuery(SELECT_BY_ORDER_ID, OrderItemPojo.class);
        query.setParameter("orderId",orderId);
        return query.getResultList();
    }
    @Transactional
    public void addOrderInCart(OrderItemPojo pojo) {
        em().persist(pojo);
    }

    public OrderItemPojo getByProductOrderId(Integer productId, Integer orderId) {
        TypedQuery<OrderItemPojo> query = em().createQuery(SELECT_BY_PRODUCT_ORDER_ID, OrderItemPojo.class);
        query.setParameter("productId",productId);
        query.setParameter("orderId",orderId);
        return getSingleRow(query);
    }
}
