package com.travel.authentication.model;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String createdAt;
}