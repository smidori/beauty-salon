package com.cct.beautysalon.models.jwt;

import com.cct.beautysalon.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private Role role;
    private String gender;
}