package com.itechart.contactmanager.payload;

import com.itechart.contactmanager.model.Address;
import com.itechart.contactmanager.model.Employee;
import com.itechart.contactmanager.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

@Data
public class EmployeeRequest {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String patronymic;
    @NotNull
    private Date birthday;
    @NotNull
    private Address address;

    public Employee create(User user) {
        Employee employee = new Employee();
        if(id != null) employee.setId(id);
        employee.setName(name);
        employee.setSurname(surname);
        employee.setPatronymic(patronymic);
        employee.setBirthday(birthday);
        employee.setAddress(address);
        employee.setUser(user);
        return employee;
    }
}
