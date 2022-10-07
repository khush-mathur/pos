package com.increff.pos.dao;

import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.BrandPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BrandDao extends AbstractDao{

    private static final String SELECT_BY_ID = "select p from Brand p where id=:id";
    private static final String SELECT_BY_BRAND_CATEGORY = "select p from Brand p where brandName= :brandName and category = :category";
    private static final String SELECT_ALL = "select p from Brand p";
    private static final String SELECT_BY_CATEGORY = "select p from Brand p where category =:category";
    private static final String SELECT_BY_BRAND = "select p from Brand p where brandName =:brandName";

    @Transactional(readOnly = true)
    public BrandPojo selectById(Integer id) {
        TypedQuery<BrandPojo> query = em().createQuery(SELECT_BY_ID, BrandPojo.class);
        query.setParameter("id", id);
        return getSingleRow(query);
    }

    @Transactional(readOnly = true)
    public BrandPojo selectByBrandCategory(String brandName,String category) {
        TypedQuery<BrandPojo> query = em().createQuery(SELECT_BY_BRAND_CATEGORY, BrandPojo.class);
        query.setParameter("brandName", brandName);
        query.setParameter("category",category);
        return getSingleRow(query);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void insert(BrandPojo brand) {
        em().persist(brand);
    }

    @Transactional(readOnly = true)
    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = em().createQuery(SELECT_ALL, BrandPojo.class);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<BrandPojo> selectByCategory(String category) {
        TypedQuery<BrandPojo> query = em().createQuery(SELECT_BY_CATEGORY, BrandPojo.class);
        query.setParameter("category",category);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<BrandPojo> selectByBrand(String brandName) {
        TypedQuery<BrandPojo> query = em().createQuery(SELECT_BY_BRAND, BrandPojo.class);
        query.setParameter("brandName",brandName);
        return query.getResultList();
    }
}
