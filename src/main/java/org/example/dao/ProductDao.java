package org.example.dao;

import org.example.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProductDao extends AbstractDao{
    private static String select_id = "select d from Product d where id = :id";
    private static String select_all = "select d from Product d";
    public void add(ProductPojo product) {
        em().persist(product);
    }

    public ProductPojo get(int id) {
        TypedQuery<ProductPojo> query = em().createQuery(select_id,ProductPojo.class);
        query.setParameter("id",id);
        return getSingleRow(query);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        TypedQuery<ProductPojo> query = em().createQuery(select_all,ProductPojo.class);
        return query.getResultList();
    }

    public void update(ProductPojo product) {
        em().merge(product);
    }
}
