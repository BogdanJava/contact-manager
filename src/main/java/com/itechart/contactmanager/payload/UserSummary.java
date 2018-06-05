package com.itechart.contactmanager.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummary {
    private long id;
    private String username;
}
