package com.itechart.contactmanager.payload;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TokenAuthenticationResponse {
    private final String token;
    private String tokenType = "Bearer";
}
