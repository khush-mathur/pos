package com.increff.pos.dao;

import com.increff.pos.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao{

    private static String SELECT_BY_ID = "select d from order_list d where id = :id";
    private static String SELECT_ALL = "select d from order_list d";

    @Transactional
    public OrderPojo add(OrderPojo order) {
         em().persist(order);
        return order;
    }

    public OrderPojo getByID(Integer id) {
        TypedQuery<OrderPojo> query = em().createQuery(SELECT_BY_ID,OrderPojo.class);
        query.setParameter("id",id);
        return getSingleRow(query);
    }

    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = em().createQuery(SELECT_ALL, OrderPojo.class);
        return query.getResultList();
    }
}
