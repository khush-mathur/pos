package com.increff.pos.dao;

import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{
    private static final String SELECT_BY_ORDER_ID = "select d from OrderItem d where orderId = :orderId";
    private static final String SELECT_ITEM_BY_ID = "select o from OrderItem o where id=:id";
    private static final String SELECT_BY_PRODUCT_ORDER_ID = "select d from OrderItem d where orderId = :orderId and productId = :productId";
    private static final String DELETE_ID = "delete from OrderItem p where id=:id";
    private static final String DELETE_BY_ORDER_ID = "delete from OrderItem p where orderId=:orderId";
    public List<OrderItemPojo> getByOrderId(Integer orderId) {
        TypedQuery<OrderItemPojo> query = em().createQuery(SELECT_BY_ORDER_ID, OrderItemPojo.class);
        query.setParameter("orderId",orderId);
        return query.getResultList();
    }
    @Transactional(rollbackFor = ApiException.class)
    public void addOrderInCart(OrderItemPojo pojo) {
        em().persist(pojo);
    }

    @Transactional(readOnly = true)
    public OrderItemPojo getByProductOrderId(Integer productId, Integer orderId) {
        TypedQuery<OrderItemPojo> query = em().createQuery(SELECT_BY_PRODUCT_ORDER_ID, OrderItemPojo.class);
        query.setParameter("productId",productId);
        query.setParameter("orderId",orderId);
        return getSingleRow(query);
    }
    @Transactional(rollbackFor = ApiException.class)
    public void deleteById(Integer id) {
        Query query = em().createQuery(DELETE_ID);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Transactional(readOnly = true)
    public OrderItemPojo getOrderItemDataById(Integer id) {
        TypedQuery<OrderItemPojo> query = em().createQuery(SELECT_ITEM_BY_ID, OrderItemPojo.class);
        query.setParameter("id",id);
        return getSingleRow(query);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void deleteByOrderId(Integer orderId) {
        Query query = em().createQuery(DELETE_BY_ORDER_ID);
        query.setParameter("orderId", orderId);
        query.executeUpdate();
    }
}
