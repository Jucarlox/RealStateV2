package com.salesianostriana.dam.realstatev2.users.dto;

import com.salesianostriana.dam.realstatev2.users.model.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private String avatar;
    private String nombre;
    private String email;
    private UserRole role;


}