package com.cct.beautysalon.models.jwt;

import com.cct.beautysalon.DTO.UserSummaryDTO;
import com.cct.beautysalon.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
    private UserSummaryDTO userDetails;
    private String message;

}
