package com.sistemasactivos.apirest.bff.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthCredentials {
    private String email;
    private String password;
}
