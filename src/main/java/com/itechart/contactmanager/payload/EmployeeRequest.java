package com.itechart.contactmanager.payload;

import com.itechart.contactmanager.model.Address;
import com.itechart.contactmanager.model.Employee;
import com.itechart.contactmanager.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class EmployeeRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String patronymic;
    @NotNull
    private Date birthday;
    private Address address;

    public Employee create(User user) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSurname(surname);
        employee.setPatronymic(patronymic);
        employee.setBirthday(birthday);
        employee.setAddress(address);
        employee.setUser(user);
        return employee;
    }
}
