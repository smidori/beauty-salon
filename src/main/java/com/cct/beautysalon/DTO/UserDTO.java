package com.cct.beautysalon.DTO;

import com.cct.beautysalon.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private UserType type;
}
