package org.example.dao;

import org.example.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class InventoryDao extends AbstractDao{

    private static final String select_id = "select p from Inventory p where id=:id";
    private static final String select_all = "select p from Inventory p";
    private static final String delete_all = "delete from Inventory p where id=:id";

    public InventoryPojo get(int id) {
        TypedQuery<InventoryPojo> query= em().createQuery(select_id, InventoryPojo.class);
        query.setParameter("id",id);
        return getSingleRow(query);
    }

    public void update(InventoryPojo existingPojo) {
        em().merge(existingPojo);
    }

    public List<InventoryPojo> getAll() {
        TypedQuery<InventoryPojo> query= em().createQuery(select_all, InventoryPojo.class);
        return query.getResultList();
    }

    public void add(InventoryPojo pojo) {
        em().persist(pojo);
    }
}
