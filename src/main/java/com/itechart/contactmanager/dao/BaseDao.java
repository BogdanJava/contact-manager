package com.itechart.contactmanager.dao;

import java.util.List;

/**
 * @author Bogdan Shishkin
 * project: contact-manager
 * date/time: 05.06.2018 / 0:26
 * email: bogdanshishkin1998@gmail.com
 */

public interface BaseDao<T> {
    void save(T entity);
    T update(T entity);
    void delete(T entity);
    T findOne(long id);
    List<T> getAll();
}
