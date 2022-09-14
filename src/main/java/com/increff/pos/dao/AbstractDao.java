package com.increff.pos.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class AbstractDao {
    @PersistenceContext
    private EntityManager em;

    EntityManager em(){
        return this.em;
    }

    <T> T getSingleRow(TypedQuery<T> query) {
        return query.getResultList().stream().findFirst().orElse(null);
    }

//    TypedQuery<BrandPojo> getQuery(String jpql) {
//        return em.createQuery(jpql, BrandPojo.class);
//    }
}
