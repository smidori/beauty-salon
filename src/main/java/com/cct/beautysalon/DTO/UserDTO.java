package com.cct.beautysalon.DTO;

import com.cct.beautysalon.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private Role role;
    private String gender;
    private String mobilePhone;
    private String homePhone;

    /**
     * constructor
     * @param id
     * @param firstName
     * @param lastName
     */
    public UserDTO(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
