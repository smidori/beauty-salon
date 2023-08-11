package com.cct.beautysalon.DTO;

import com.cct.beautysalon.enums.Role;
import com.cct.beautysalon.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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

    /**
     * Convert to entity
     * @param dto
     * @return
     */
    public static User toEntity(UserSummaryDTO dto){
        if(dto == null)
            return null;
        return User.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .login(dto.getLogin())
                .role(dto.getRole())
                .mobilePhone(dto.getMobilePhone())
                .build();
    }
    
}
