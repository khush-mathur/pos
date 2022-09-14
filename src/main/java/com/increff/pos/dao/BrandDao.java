package com.increff.pos.dao;

import com.increff.pos.pojo.BrandPojo;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BrandDao extends AbstractDao{

    private static final String SELECT_BY_ID = "select p from Brand p where id=:id";
    private static final String SELECT_BY_BRAND_CATEGORY = "select p from Brand p where brandName= :brandName and category = :category";
    private static final String SELECT_ALL = "select p from Brand p";
    private static final String SELECT_BY_CATEGORY = "select p from Brand p where category =:category";
    private static final String SELECT_BY_BRAND = "select p from Brand p where brandName =:brand";
    private static final String DELETE_ALL = "delete from Brand p where id=:id";

    public BrandPojo selectById(Integer id) {
        TypedQuery<BrandPojo> query = em().createQuery(SELECT_BY_ID, BrandPojo.class);
        query.setParameter("id", id);
        return getSingleRow(query);
    }

    public BrandPojo selectByBrandCategory(String brandName,String category) {
        TypedQuery<BrandPojo> query = em().createQuery(SELECT_BY_BRAND_CATEGORY, BrandPojo.class);
        query.setParameter("brandName", brandName);
        query.setParameter("category",category);
        return getSingleRow(query);
    }

    @Transactional
    public void insert(BrandPojo brand) {
        em().persist(brand);
    }

    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = em().createQuery(SELECT_ALL, BrandPojo.class);
        return query.getResultList();
    }

    @Transactional
    public BrandPojo update(BrandPojo brand){
        em().merge(brand);
        return brand;
    }

    @Transactional
    public void delete(Integer id) {
        Query query = em().createQuery(DELETE_ALL);
        query.setParameter("id", id);
        query.executeUpdate();
    }

//    public List<BrandPojo> getByBrandCategory(String brandName, String category) {
//        TypedQuery<BrandPojo> query = em().createQuery(SELECT_ALL, BrandPojo.class);
//        return query.getResultList();
//    }

    public List<BrandPojo> getByCategory(String category) {
        TypedQuery<BrandPojo> query = em().createQuery(SELECT_BY_CATEGORY, BrandPojo.class);
        query.setParameter("category",category);
        return query.getResultList();
    }

    public List<BrandPojo> getByBrand(String brandName) {
        TypedQuery<BrandPojo> query = em().createQuery(SELECT_BY_BRAND, BrandPojo.class);
        query.setParameter("brandName",brandName);
        return query.getResultList();
    }
}
