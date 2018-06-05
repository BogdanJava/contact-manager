package com.itechart.contactmanager.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 15)
    private String username;
    @NotBlank
    @Size(max = 40)
    private String password;
}
