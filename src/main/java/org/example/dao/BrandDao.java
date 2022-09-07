package org.example.dao;

import org.example.pojo.BrandPojo;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BrandDao extends AbstractDao{

    private static final String select_id = "select p from Brand p where id=:id";
    private static final String select_all = "select p from Brand p";
    private static final String delete_all = "delete from Brand p where id=:id";

    public BrandPojo select(int id) {
        TypedQuery<BrandPojo> query = em().createQuery(select_id, BrandPojo.class);
        query.setParameter("id", id);
        return getSingleRow(query);
    }

    @Transactional
    public void insert(BrandPojo brand) {
        em().persist(brand);
    }

    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = em().createQuery(select_all, BrandPojo.class);
        return query.getResultList();

    }

    @Transactional
    public BrandPojo update(BrandPojo brand){
        em().merge(brand);
        return brand;
    }

    @Transactional
    public void delete(int id) {
        Query query = em().createQuery(delete_all);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
