package com.sistemasactivos.accountweb.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthCredentials {
    private String email;
    private String password;
}
