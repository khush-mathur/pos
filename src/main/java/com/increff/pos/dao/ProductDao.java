package com.increff.pos.dao;

import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ProductDao extends AbstractDao{
    private static String SELECT_BY_ID = "select d from Product d where id = :id";
    private static String SELECT_BY_BARCODE = "select d from Product d where barcode = :barcode";
    private static String SELECT_ALL = "select d from Product d";

    @Transactional(rollbackFor = ApiException.class)
    public void add(ProductPojo product) {
        em().persist(product);
    }

    @Transactional(readOnly = true)
    public ProductPojo getByID(Integer id) {
        TypedQuery<ProductPojo> query = em().createQuery(SELECT_BY_ID,ProductPojo.class);
        query.setParameter("id",id);
        return getSingleRow(query);
    }

    @Transactional(readOnly = true)
    public ProductPojo getByBarcode(String barcode) {
        TypedQuery<ProductPojo> query = em().createQuery(SELECT_BY_BARCODE,ProductPojo.class);
        query.setParameter("barcode",barcode);
        return getSingleRow(query);
    }

    @Transactional(readOnly = true)
    public List<ProductPojo> getAll() {
        TypedQuery<ProductPojo> query = em().createQuery(SELECT_ALL,ProductPojo.class);
        return query.getResultList();
    }
}
