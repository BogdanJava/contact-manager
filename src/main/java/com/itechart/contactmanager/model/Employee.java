package com.itechart.contactmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author Bogdan Shishkin
 * project: contact-manager
 * date/time: 05.06.2018 / 0:06
 * email: bogdanshishkin1998@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue
    private long id;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @OneToMany(mappedBy = "employee")
    private Set<Phone> phones;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String surname;
    private String patronymic;
}