package com.increff.pos.dao;

import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class InventoryDao extends AbstractDao{

    private static final String SELECT_BY_ID = "select p from Inventory p where id=:id";
    private static final String select_all = "select p from Inventory p";

    @Transactional(readOnly = true)
    public InventoryPojo getByProductId(Integer id) {
        TypedQuery<InventoryPojo> query= em().createQuery(SELECT_BY_ID, InventoryPojo.class);
        query.setParameter("id",id);
        return getSingleRow(query);
    }

    @Transactional(readOnly = true)
    public List<InventoryPojo> getAll() {
        TypedQuery<InventoryPojo> query= em().createQuery(select_all, InventoryPojo.class);
        return query.getResultList();
    }

    @Transactional(rollbackFor = ApiException.class)
    public void add(InventoryPojo pojo) {
        em().persist(pojo);
    }
}
