package com.itechart.contactmanager.payload;

import com.itechart.contactmanager.model.Employee;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PhoneRequest {
    @NotNull
    private Long employeeId;
    @NotBlank
    @Size(min = 7, max = 14)
    private String number;
}
