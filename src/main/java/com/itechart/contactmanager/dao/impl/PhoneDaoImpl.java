package com.itechart.contactmanager.dao.impl;

import com.itechart.contactmanager.dao.PhoneDao;
import com.itechart.contactmanager.model.Phone;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * @author Bogdan Shishkin
 * project: contact-manager
 * date/time: 05.06.2018 / 0:31
 * email: bogdanshishkin1998@gmail.com
 */

@Repository
public class PhoneDaoImpl extends BaseDaoImpl<Phone> implements PhoneDao {
    {
        this.entityClass = Phone.class;
    }

    @Override
    public Phone findByNumber(String number) {
        return entityManager.createQuery("SELECT p FROM Phone p WHERE p.number = :number", entityClass)
                .setParameter("number", number).getSingleResult();
    }

    @Override
    public List<Phone> getByEmployeeId(long employeeId) {
        return entityManager.createQuery("SELECT p FROM Phone p WHERE p.employee.id = :employeeId", entityClass)
                .setParameter("employeeId", employeeId).getResultList();
    }

    @Override
    public boolean exists(String number) {
        try{
            findByNumber(number);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
