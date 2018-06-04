package com.itechart.contactmanager.dao.impl;

import com.itechart.contactmanager.dao.BaseDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Bogdan Shishkin
 * project: contact-manager
 * date/time: 05.06.2018 / 0:27
 * email: bogdanshishkin1998@gmail.com
 */

public class BaseDaoImpl<T> implements BaseDao<T> {

    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public T findOne(long id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<T> getAll() {
        return entityManager
                .createQuery(String.format("SELECT t FROM %s t", entityClass.getSimpleName()), entityClass)
                .getResultList();
    }
}
