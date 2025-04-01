package com.travel.authentication.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthDTO {
    private String name;
    private String username;
    private String email;
    private String password;
}
