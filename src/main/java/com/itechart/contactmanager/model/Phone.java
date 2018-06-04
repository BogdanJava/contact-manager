package com.itechart.contactmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Bogdan Shishkin
 * project: contact-manager
 * date/time: 05.06.2018 / 0:08
 * email: bogdanshishkin1998@gmail.com
 */

@Data
@Entity
@Table(name = "phone", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"number"})
})
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String number;
}
