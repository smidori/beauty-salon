package com.cct.beautysalon.DTO;

import com.cct.beautysalon.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserSummaryDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private Role role;
    private String mobilePhone;
    
}
